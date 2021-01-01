package com.github.network.services

import com.github.network.entities.AccessTokenRequest
import com.github.network.entities.DeviceAndUserCodeRequest
import com.github.network.retrofit
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface AuthApi {
    @POST("/login/device/code")
    fun getDeviceAndUserCode(@Body request: DeviceAndUserCodeRequest): Observable<ResponseBody>

    @POST("/login/oauth/access_token")
    fun getAccessToken(@Body request: AccessTokenRequest): Observable<ResponseBody>
}

object AuthService : AuthApi by retrofit.create(AuthApi::class.java)