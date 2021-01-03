package com.github.network.interceptors

import com.github.model.account.AccountManager
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Chain): Response {
        val original = chain.request()
        return chain.proceed(original.newBuilder().apply {
            header("Authorization", "Token ${AccountManager.token}")
//            when {
//                original.url().pathSegments().contains("authorizations") -> {
//                    val userCredentials = "${AccountManager.username}:${AccountManager.password}"
//                    val auth = "Basic ${String(Base64.encode(userCredentials.toByteArray(), Base64.DEFAULT)).trim()}"
//                    header("Authorization", auth)
//                }
//                AccountManager.isLoggedIn() -> {
//                    header("Authorization", "Token ${AccountManager.token}")
//                }
//                else -> removeHeader("Authorization")
//            }
        }.build())
    }
}