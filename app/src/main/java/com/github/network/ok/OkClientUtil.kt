package com.github.network.ok

import com.github.network.dns.modifyDns
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

fun createCommonClientBuilder() = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .modifyDns()