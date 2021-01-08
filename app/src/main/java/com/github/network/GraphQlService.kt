package com.github.network

import com.apollographql.apollo.ApolloClient
import com.github.graphql.entities.RepositoryIssueCountQuery
import com.github.network.interceptors.AuthInterceptor
import com.github.network.ok.createCommonClientBuilder
import com.github.retroapollo.RetroApollo
import com.github.retroapollo.annotations.GraphQLQuery
import com.github.retroapollo.rxjava.RxJavaCallAdapterFactory
import okhttp3.logging.HttpLoggingInterceptor
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

private const val BASE_URL = "https://api.github.com/graphql"

interface GraphQlApi {
    fun repositoryIssuesCount(
        @GraphQLQuery("owner") owner: String,
        @GraphQLQuery("repoName") repoName: String,
    ): Observable<RepositoryIssueCountQuery.Data>
}

private val graphQlService by lazy {
    RetroApollo.Builder()
        .apolloClient(apolloClient)
        .addCallAdapterFactory(
            RxJavaCallAdapterFactory()
                .observableScheduler(AndroidSchedulers.mainThread())
                .subscribeScheduler(Schedulers.io())
        )
        .build()
        .createGraphQLService(GraphQlApi::class)
}

object GraphQlService : GraphQlApi by graphQlService

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