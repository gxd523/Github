package com.github.network.interceptors

import com.github.common.log.logger
import com.github.common.no
import com.github.common.otherwise
import com.github.common.yes
import com.github.network.services.FORCE_NETWORK
import com.github.util.NetworkUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Chain): Response {
        var request = chain.request()
        request = NetworkUtil.isAvailable().no {
            request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }.otherwise {// 网络正常情况下，如果设置了FORCE_NETWORK，让缓存失效，从而强制网络请求
            request.url().queryParameter(FORCE_NETWORK)?.toBoolean()?.let {
                it.yes {
                    //注意 noCache | noStore，前者不会读缓存；后者既不读缓存，也不对响应进行缓存
                    //尽管看上去 noCache 比较符合我们的需求，但服务端仍然可能返回服务端的缓存
                    request.newBuilder().cacheControl(CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build()).build()
                }.otherwise {
                    request
                }
            } ?: request
        }

        request = request.newBuilder().url(request.url().newBuilder().removeAllQueryParameters(FORCE_NETWORK).build()).build()
        return chain.proceed(request).also { response ->
            logger.debug("Cache: ${response.cacheResponse()}, Network: ${response.networkResponse()}")
        }
    }
}