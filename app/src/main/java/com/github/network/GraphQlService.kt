package com.github.network

import com.apollographql.apollo.ApolloClient
import com.github.graphql.entities.RepositoryIssueCountQuery
import com.github.network.interceptors.AuthInterceptor
import com.github.network.ok.createCommonClientBuilder
import com.github.retroapollo.RetroApollo
import com.github.retroapollo.annotations.GraphQlQuery
import com.github.retroapollo.coroutine.CoroutineCallAdapterFactory
import com.github.retroapollo.rxjava.RxJavaCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.logging.HttpLoggingInterceptor
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

private const val BASE_URL = "https://api.github.com/graphql"

interface GraphQlApi {
    fun repositoryIssuesCount(
        @GraphQlQuery("owner") owner: String,
        @GraphQlQuery("repoName") repoName: String,
    ): Observable<RepositoryIssueCountQuery.Data>

    fun repositoryIssuesCountAsync(
        @GraphQlQuery("owner") owner: String,
        @GraphQlQuery("repoName") repoName: String,
    ): Deferred<RepositoryIssueCountQuery.Data>
}

private val graphQlService by lazy {
    RetroApollo.Builder()
        .apolloClient(apolloClient)
        .addCallAdapterFactory(
            RxJavaCallAdapterFactory()
                .observableScheduler(AndroidSchedulers.mainThread())
                .subscribeScheduler(Schedulers.io())
        )
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
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