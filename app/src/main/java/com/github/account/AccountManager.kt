package com.github.account

import com.github.network.entities.AuthorizationRequest
import com.github.network.entities.AuthorizationResponse
import com.github.network.entities.User
import com.github.network.services.AuthService
import com.github.network.services.UserService
import com.github.util.fromJson
import com.github.util.sp
import com.google.gson.Gson
import retrofit2.HttpException
import rx.Observable

interface OnAccountStateChangeListener {
    fun onLogin(user: User)

    fun onLogout()
}

object AccountManager {
    var authId by sp(-1)
    var username by sp("")
    var password by sp("")
    var token by sp("")

    private var userJson by sp("")

    var currentUser: User? = null
        get() {
            if (field == null && userJson.isNotEmpty()) {
                field = Gson().fromJson(userJson)
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

    private val onAccountStateChangeListeners = ArrayList<OnAccountStateChangeListener>()

    private fun notifyLogin(user: User) {
        onAccountStateChangeListeners.forEach {
            it.onLogin(user)
        }
    }

    private fun notifyLogout() {
        onAccountStateChangeListeners.forEach { it.onLogout() }
    }

    fun isLoggedIn(): Boolean = token.isNotEmpty()

    fun login() =
        AuthService.createAuthorization(AuthorizationRequest())
            .doOnNext {
                if (it.token.isEmpty()) throw AccountException(it)
            }
            .retryWhen {
                it.flatMap {
                    if (it is AccountException) {
                        AuthService.deleteAuthorization(it.authorizationRsp.id)
                    } else {
                        Observable.error(it)
                    }
                }
            }
            .flatMap {
                token = it.token
                authId = it.id
                UserService.getAuthenticatedUser()
            }
            .map {
                currentUser = it
                notifyLogin(it)
            }

    fun logout() = AuthService.deleteAuthorization(authId)
        .doOnNext {
            if (it.isSuccessful) {
                authId = -1
                token = ""
                currentUser = null
                notifyLogout()
            } else {
                throw HttpException(it)
            }
        }

    class AccountException(val authorizationRsp: AuthorizationResponse) : Exception("Already logged in.")
}