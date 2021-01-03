package com.github.login

import android.util.Log
import com.github.model.account.AccountManager
import com.github.mvp.impl.BasePresenter
import com.github.network.entities.DeviceAndUserCodeResponse
import com.github.ui.login.LoginActivity
import rx.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class LoginPresenter : BasePresenter<LoginActivity>() {
    fun getDeviceAndUserCode(action: (DeviceAndUserCodeResponse) -> Unit) {
        AccountManager.requestDeviceAndUserCode()
            .subscribe(
                {
                    Log.d("gxd", "device user code...$it")
                    action(it)
                }, {
                    Log.d("gxd", "device user code error...${it.message}")
                }
            )
    }

    fun getAccessToken(device_code: String, action: () -> Unit) {
        requestAccessTokenDelay(device_code, action, 5, 10)
    }

    private fun requestAccessTokenDelay(device_code: String, action: () -> Unit, delay: Long, remainTime: Int) {
        Observable.timer(delay, TimeUnit.SECONDS)
            .flatMap {
                AccountManager.requestAccessToken(device_code)
            }
            .map {
                it.string()
            }
            .subscribe(
                {
                    if (it.isEmpty() || it.contains("error")) {
                        var nextDelay = 5L
                        if (it.contains("&interval=")) {
                            nextDelay = it.substring(it.indexOf("&interval=") + 10).toLong()
                        }
                        requestAccessTokenDelay(device_code, action, nextDelay, remainTime - 1)
                        return@subscribe
                    }
                    var token = ""
                    it.split("&").forEach {
                        val split = it.split("=")
                        if ("access_token" == split[0]) {
                            token = split[1]
                            return@forEach
                        }
                    }
                    AccountManager.token = token
                    Log.d("gxd", "access token...$it")
                    action()
                }, {
                    Log.d("gxd", "access token error....${it.message}")
                }
            )
    }

    fun getUser(succeed: () -> Unit, error: (Throwable) -> Unit) {
        AccountManager.getUser()
            .subscribe(
                {
                    Log.d("gxd", "user...${it}")
                    succeed()
                }, {
                    Log.d("gxd", "user error...${it.message}")
                    error(it)
                }
            )
    }
}