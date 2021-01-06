package com.github.network.services

import com.github.network.entities.Repository
import com.github.network.entities.SearchRepositoryList
import com.github.network.retrofit
import retrofit2.adapter.rxjava.GitHubPaging
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface RepositoryApi {
    @GET("/users/{owner}/repos?type=all")
    fun repositoryListOfUser(
        @Path("owner") owner: String,
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 20,
    ): Observable<GitHubPaging<Repository>>

    @GET("/search/repositories?order=desc&sort=updated")
    fun repositoryListOfSearch(
        @Query("page") page: Int = 1,
        @Query("q") q: String,
        @Query("per_page") per_page: Int = 20,
    ): Observable<SearchRepositoryList>

    @GET("/repos/{owner}/{repo}")
    fun getRepository(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query(FORCE_NETWORK) forceNetwork: Boolean = false,
    ): Observable<Repository>
}

/**
 * 通过一个 QueryParameter 让 CacheInterceptor 添加 no-cache
 */
const val FORCE_NETWORK = "forceNetwork"

object RepositoryService : RepositoryApi by retrofit.create(RepositoryApi::class.java)