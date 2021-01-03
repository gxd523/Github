package com.github.model.account

import com.github.network.entities.AccessTokenRequest
import com.github.network.entities.DeviceAndUserCodeRequest
import com.github.network.entities.DeviceAndUserCodeResponse
import com.github.network.entities.User
import com.github.network.services.AuthService
import com.github.network.services.UserService
import com.github.util.fromJson
import com.github.util.sp
import com.google.gson.Gson
import rx.Observable

interface OnAccountStateChangeListener {
    fun onLogin(user: User)

    fun onLogout()
}

object AccountManager {
    var token by sp("")

    private var userJson by sp("")

    internal var currentUser: User? = null
        get() {
            if (field == null && userJson.isNotEmpty()) {
                field = Gson().fromJson<User>(userJson)
            }
            return field
        }
        set(value) {
            userJson = if (value == null) {
                ""
            } else {
                Gson().toJson(value)
            }
            field = value
        }

    internal val onAccountStateChangeListeners = ArrayList<OnAccountStateChangeListener>()

    private fun notifyLogin(user: User) {
        onAccountStateChangeListeners.forEach {
            it.onLogin(user)
        }
    }

    private fun notifyLogout() {
        onAccountStateChangeListeners.forEach { it.onLogout() }
    }

    fun isLoggedIn(): Boolean = currentUser != null

    fun requestDeviceAndUserCode(): Observable<DeviceAndUserCodeResponse> =
        AuthService.getDeviceAndUserCode(DeviceAndUserCodeRequest())
            .map {
                var device_code = ""
                var user_code = ""
                var verification_uri = ""
                var expires_in: Int = -1
                var interval: Int = -1
                it.string().split("&").forEach {
                    val keyValue = it.split("=")
                    when (keyValue[0]) {
                        "device_code" -> device_code = keyValue[1]
                        "user_code" -> user_code = keyValue[1]
                        "verification_uri" -> verification_uri = keyValue[1].replace("%3A", ":").replace("%2F", "/")
                        "expires_in" -> expires_in = keyValue[1].toInt()
                        "interval" -> interval = keyValue[1].toInt()
                    }
                }
                DeviceAndUserCodeResponse(device_code, user_code, verification_uri, expires_in, interval)
            }

    fun requestAccessToken(device_code: String) = AuthService.getAccessToken(AccessTokenRequest(device_code = device_code))

    fun getUser(): Observable<User> = UserService.getAuthenticatedUser()
        .doOnError {
            logout()
        }
        .map {
            currentUser = it
            notifyLogin(it)
            it
        }

    fun logout() {
        if (currentUser != null) {
            currentUser = null
            notifyLogout()
        }
    }
}