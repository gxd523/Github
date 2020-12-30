package com.github.login

import android.os.Looper
import com.github.BuildConfig
import com.github.LoginActivity
import com.github.account.AccountManager
import com.github.common.otherwise
import com.github.common.yes
import com.github.mvp.impl.BasePresenter

class LoginPresenter : BasePresenter<LoginActivity>() {
    fun doLogin(username: String, password: String) {
        AccountManager.username = username
        AccountManager.password = password
        viewer.onLoginStart()
        AccountManager.login()
            .subscribe({
                viewer.onLoginSuccess()
            }, {
                viewer.onLoginError(it)
            })
    }

    fun checkUsername(username: String): Boolean = true

    fun checkPassword(password: String): Boolean = true

    override fun onResume() {
        super.onResume()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Looper.getMainLooper().queue
        } else {
            Looper.myQueue()
        }.addIdleHandler {
            viewer.setData(
                BuildConfig.DEBUG.yes { BuildConfig.debugUsername }.otherwise { AccountManager.username },
                BuildConfig.DEBUG.yes { BuildConfig.debugPassword }.otherwise { AccountManager.password },
            )
            false
        }
    }
}