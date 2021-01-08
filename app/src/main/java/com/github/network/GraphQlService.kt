package com.github.network

import com.apollographql.apollo.ApolloClient
import com.github.network.interceptors.AuthInterceptor
import com.github.network.ok.createCommonClientBuilder
import okhttp3.logging.HttpLoggingInterceptor

private const val BASE_URL = "https://api.github.com/graphql"

val apolloClient: ApolloClient by lazy {
    ApolloClient.builder()
        .serverUrl(BASE_URL)
        .okHttpClient(
            createCommonClientBuilder()
                .addInterceptor(AuthInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build()
        )
        .build()
}