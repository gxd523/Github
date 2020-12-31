package com.github

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.bennyhuo.common.ext.hideSoftInput
import com.github.common.otherwise
import com.github.common.yes
import com.github.login.LoginPresenter
import com.github.mvp.impl.BaseActivity
import com.github.settings.Configs
import kotlinx.android.synthetic.main.activity_login.*
import me.zhyd.oauth.config.AuthConfig
import me.zhyd.oauth.request.AuthGithubRequest
import me.zhyd.oauth.request.AuthRequest
import me.zhyd.oauth.utils.AuthStateUtils
import org.jetbrains.anko.toast

class LoginActivity : BaseActivity<LoginPresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInBtn.setOnClickListener {
            val username = usernameEt.text.toString()
            val password = passwordEt.text.toString()
            presenter.checkUsername(username)
                .yes {
                    presenter.checkPassword(password)
                        .yes {
                            hideSoftInput()
                            presenter.doLogin(username, password)
                        }.otherwise {
                            showTips(passwordEt, "密码不合法")
                        }
                }.otherwise {
                    showTips(usernameEt, "用户名不合法")
                }
        }
    }

    private fun justAuth() {
        val authConfig = AuthConfig.builder()
            .clientId(Configs.Account.clientId)
            .clientSecret(Configs.Account.clientSecret)
            .redirectUri(Configs.Account.callbackUrl)
            .build()
        val authRequest: AuthRequest = AuthGithubRequest(authConfig)
        val authorize = authRequest.authorize(AuthStateUtils.createState())
        Log.d("gxd", "LoginActivity.onCreate-->$authorize")
    }

    private fun showTips(view: EditText, tips: String) {
        view.requestFocus()
        view.error = tips
    }

    private fun showLoading(show: Boolean) {
        loading
            .animate()
            .setDuration(200)
            .alpha((if (show) 1 else 0).toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    loading.visibility = if (show) View.VISIBLE else View.GONE
                }
            })
    }

    fun onLoginStart() {
        showLoading(true)
    }

    fun onLoginError(e: Throwable) {
        showLoading(false)
        e.printStackTrace()
        toast("登录失败")
    }

    fun onLoginSuccess() {
        showLoading(false)
        toast("登陆成功")
    }

    fun setData(username: String, password: String) {
        usernameEt.setText(username)
        passwordEt.setText(password)
    }
}
