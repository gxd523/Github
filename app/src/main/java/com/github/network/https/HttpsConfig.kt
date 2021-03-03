package com.github.network.https

import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * 信任指定证书(服务端返回的公钥，经过CA加密后的证书)，客户端验证服务端返回公钥的具体实现
 * getAssets().open("srca.cer")
 */
fun OkHttpClient.Builder.trustCertificate(vararg certificateStream: InputStream): OkHttpClient.Builder {
    // 创建X.509格式的CertificateFactory
    val certificateFactory: CertificateFactory = CertificateFactory.getInstance("X.509")
    // 创建一个默认类型的KeyStore，存储我们信任的证书
    val keyStore: KeyStore = KeyStore.getInstance(KeyStore.getDefaultType())
    keyStore.load(null, "password".toCharArray())

    for (i in certificateStream.indices) {
        certificateStream[i].use {
            // 1、CertificateFactory根据证书文件流生成证书Certificate
            val certificate: Certificate = certificateFactory.generateCertificate(it)
            val certificateAlias = i.toString()
            // 2、将certificate作为信任的证书放入到keyStore中
            keyStore.setCertificateEntry(certificateAlias, certificate)
        }
    }

    // 3、用我们之前的keyStore实例初始化TrustManagerFactory，这样trustManagerFactory就会信任keyStore中的证书
    val trustManagerFactory: TrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(keyStore)

    // 4、通过TrustManagerFactory获取TrustManager数组，TrustManager也会信任keyStore中的证书
    val trustManagers = trustManagerFactory.trustManagers
    if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
        throw IllegalStateException("Unexpected default trust managers:${trustManagers.contentDeepToString()}")
    }

    // 5、用上面得到的trustManagers初始化SSLContext，这样sslContext就会信任keyStore中的证书
    val sslContext: SSLContext = SSLContext.getInstance("TLS")
    sslContext.init(null, trustManagers, SecureRandom())

    sslSocketFactory(sslContext.socketFactory, trustManagers[0] as X509TrustManager)
    hostnameVerifier { _, _ -> true }
    return this
}

/**
 * 不对证书做检查，始终信任证书
 */
fun OkHttpClient.Builder.trustCertificate(): OkHttpClient.Builder {
    val trustManager = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) = TODO()

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) = TODO()

        override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
    }

    val sslContext: SSLContext = SSLContext.getInstance("TLS")
    sslContext.init(null, arrayOf(trustManager), null)

    sslSocketFactory(sslContext.socketFactory, trustManager)
    hostnameVerifier { _, _ -> true }
    return this
}