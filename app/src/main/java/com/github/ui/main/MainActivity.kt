package com.github.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.bennyhuo.tieguanyin.annotations.Builder
import com.github.R
import com.github.common.no
import com.github.common.otherwise
import com.github.model.account.AccountManager
import com.github.model.account.OnAccountStateChangeListener
import com.github.network.entities.User
import com.github.ui.about.AboutFragment
import com.github.ui.login.startLoginActivity
import com.github.util.doOnLayoutAvailable
import com.github.util.loadWithGlide
import com.github.util.showFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk15.listeners.onClick

@Builder(flags = [Intent.FLAG_ACTIVITY_CLEAR_TOP])
class MainActivity : AppCompatActivity() {
    private val accountListener by lazy {
        object : OnAccountStateChangeListener {
            override fun onLogin(user: User) {
                updateNavigationView(user)
            }

            override fun onLogout() {
                clearNavigationView()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tool_bar)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            tool_bar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        initNavigationView()

        AccountManager.onAccountStateChangeListeners += accountListener

        showFragment(R.id.fragmentContainer, AboutFragment::class.java)
    }

    private fun initNavigationView() {
        AccountManager.currentUser?.let(::updateNavigationView) ?: clearNavigationView()
        initNavigationHeaderEvent()
    }

    private fun initNavigationHeaderEvent() {
        navigationView.doOnLayoutAvailable {
            navigationHeader.onClick {
                AccountManager.isLoggedIn().no {
                    startLoginActivity()
                }.otherwise {
                    AccountManager.logout()
                }
            }
        }
    }

    private fun updateNavigationView(user: User) {
        navigationView.doOnLayoutAvailable {
            usernameView.text = user.login
            emailView.text = user.email ?: user.html_url
            avatarView.loadWithGlide(user.avatar_url, user.login.first())
        }
    }

    private fun clearNavigationView() {
        navigationView.doOnLayoutAvailable {
            usernameView.text = "请登录"
            emailView.text = ""
            avatarView.imageResource = R.drawable.ic_github
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        AccountManager.onAccountStateChangeListeners -= accountListener
        super.onDestroy()
    }
}
