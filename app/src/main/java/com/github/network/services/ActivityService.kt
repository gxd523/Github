package com.github.network.services

import com.github.network.entities.Repository
import com.github.network.entities.SubscriptionBody
import com.github.network.entities.SubscriptionResponse
import com.github.network.entities.WATCH
import com.github.network.retrofit
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.adapter.rxjava.GitHubPaging
import retrofit2.http.*
import rx.Observable

interface ActivityApi {
    //region star
    @GET("/user/starred/{owner}/{repo}")
    fun isStarred(@Path("owner") owner: String, @Path("repo") repo: String): Observable<Response<Any>>

    @PUT("/user/starred/{owner}/{repo}")
    fun star(@Path("owner") owner: String, @Path("repo") repo: String): Observable<Any>

    @DELETE("/user/starred/{owner}/{repo}")
    fun unstar(@Path("owner") owner: String, @Path("repo") repo: String): Observable<Any>

    @GET("/users/{username}/starred")
    fun reposStarredBy(@Path("username") username: String): Observable<GitHubPaging<Repository>>

    @GET("/user/starred")
    fun reposStarredByCurrentUser(): Observable<GitHubPaging<Repository>>

    //region watch / subscription
    @GET("/repos/{owner}/{repo}/subscription")
    fun isWatchedAsync(@Path("owner") owner: String, @Path("repo") repo: String): Deferred<SubscriptionResponse>

    @PUT("/repos/{owner}/{repo}/subscription")
    @Headers("Content-Type:application/json")
    fun watch(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body subscriptionBody: SubscriptionBody = WATCH,
    ): Observable<SubscriptionResponse>

    @DELETE("/repos/{owner}/{repo}/subscription")
    fun unwatch(@Path("owner") owner: String, @Path("repo") repo: String): Observable<Any>

    @GET("/users/{username}/subscriptions")
    fun reposWatchedBy(@Path("username") username: String): Observable<GitHubPaging<Repository>>

    @GET("/user/subscriptions")
    fun reposWatchedByCurrentUser(): Observable<GitHubPaging<Repository>>
}

object ActivityService : ActivityApi by retrofit.create(ActivityApi::class.java)