package com.github

import android.os.Bundle
import com.github.login.LoginPresenter
import com.github.mvp.impl.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginPresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username.setText(Settings.username)
        password.setText(Settings.password)
    }
}
