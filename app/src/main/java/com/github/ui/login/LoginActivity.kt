package com.github.ui.login

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bennyhuo.tieguanyin.annotations.Builder
import com.github.R
import com.github.mvp.impl.BaseActivity
import com.github.presenter.LoginPresenter
import com.github.ui.main.startMainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

@Builder(flags = [Intent.FLAG_ACTIVITY_NO_HISTORY])
class LoginActivity : BaseActivity<LoginPresenter>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        showLoading(true)

        presenter.getUser({
            onLoginSuccess()
        }, {
            presenter.getDeviceAndUserCode { deviceAndUserCodeResponse ->
                loading.visibility = View.GONE
                text_view.text =
                    "请打开以下链接：\n${deviceAndUserCodeResponse.verification_uri}\n并输入验证码：${deviceAndUserCodeResponse.user_code}"

                val clipboardManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                // 创建普通字符型ClipData
                val mClipData = ClipData.newPlainText("Label", deviceAndUserCodeResponse.user_code)
                // 将ClipData内容放到系统剪贴板里。
                clipboardManager.setPrimaryClip(mClipData)

                presenter.getAccessToken(deviceAndUserCodeResponse.device_code) {
                    presenter.getUser({
                        onLoginSuccess()
                    }, {
                        onLoginError(it)
                    })
                }
            }
        })
    }

    private fun showLoading(show: Boolean) {
        loading.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun onLoginError(e: Throwable) {
        showLoading(false)
        e.printStackTrace()
        toast("登录失败")
    }

    private fun onLoginSuccess() {
        showLoading(false)
        toast("登陆成功")
        startMainActivity()
    }
}
