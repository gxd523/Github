package com.github.network

import com.github.AppContext
import com.github.common.ensureDir
import com.github.network.dns.modifyDns
import com.github.network.interceptors.AcceptInterceptor
import com.github.network.interceptors.AuthInterceptor
import com.github.network.interceptors.CacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory2
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.File
import java.util.concurrent.TimeUnit

val authRetrofit: Retrofit by lazy {
    val clientBuilder = createClientBuilder()
    createRetrofitBuilder(clientBuilder.build())
        .baseUrl("https://github.com")
        .build()
}

val retrofit: Retrofit by lazy {
    val clientBuilder = createClientBuilder()
    clientBuilder.apply {
        addInterceptor(AcceptInterceptor())
        addInterceptor(AuthInterceptor())
        addInterceptor(CacheInterceptor())
    }
    createRetrofitBuilder(clientBuilder.build())
        .baseUrl("https://api.github.com")
        .build()
}

private val cacheFile by lazy {
    File(AppContext.cacheDir, "webServiceApi").apply { ensureDir() }
}

private fun createClientBuilder() = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .cache(Cache(cacheFile, 50 * 1024 * 1024))
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .modifyDns()


private fun createRetrofitBuilder(client: OkHttpClient) = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJavaCallAdapterFactory2.createWithSchedulers(Schedulers.io(), AndroidSchedulers.mainThread()))
    .client(client)