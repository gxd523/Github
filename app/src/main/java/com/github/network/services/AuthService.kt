package com.github.network.services

import com.github.network.entities.AuthorizationRequest
import com.github.network.entities.AuthorizationResponse
import com.github.network.retrofit
import com.github.settings.Configs
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
import rx.Observable

interface AuthApi {
    @PUT("/authorizations/clients/${Configs.Account.clientId}/{fingerPrint}")
    fun createAuthorization(
        @Body request: AuthorizationRequest,
        @Path("fingerPrint") fingerPrint: String = Configs.Account.fingerPrint,
    ): Observable<AuthorizationResponse>

    @DELETE("/authorizations/{id}")
    fun deleteAuthorization(@Path("id") id: Int): Observable<Response<Any>>
}

object AuthService : AuthApi by retrofit.create(AuthApi::class.java)