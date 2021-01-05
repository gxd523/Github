package com.github.ui.main

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.github.R
import com.github.common.no
import com.github.common.otherwise
import com.github.common.yes
import com.github.model.account.AccountManager
import com.github.model.account.OnAccountStateChangeListener
import com.github.navigation.MenuItemWrapper
import com.github.navigation.NavigationController
import com.github.navigation.afterClosed
import com.github.network.entities.User
import com.github.ui.login.LoginActivity
import com.github.ui.view.widget.ActionBarController
import com.github.util.launchActivity
import com.github.util.showFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val actionBarController by lazy {
        ActionBarController(tabLayout)
    }

    private val navigationController by lazy {
        NavigationController(navigationView, ::onNavItemChanged, ::handleNavigationHeaderClickEvent)
    }

    private val accountListener by lazy {
        object : OnAccountStateChangeListener {
            override fun onLogin(user: User) {
                navigationController.useLoginLayout()
            }

            override fun onLogout() {
                navigationController.useNoLoginLayout()
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
    }

    private fun initNavigationView() {
        AccountManager.isLoggedIn()
            .yes {
                navigationController.useLoginLayout()
            }
            .otherwise {
                navigationController.useNoLoginLayout()
            }
        navigationController.selectProperItem()
    }

    private fun onNavItemChanged(menuItemWrapper: MenuItemWrapper) {
        drawer_layout.afterClosed {
            showFragment(R.id.fragmentContainer, menuItemWrapper.fragmentClass, menuItemWrapper.arguements)
            title = menuItemWrapper.title
        }
    }

    private fun handleNavigationHeaderClickEvent() {
        AccountManager.isLoggedIn().no {
            launchActivity<LoginActivity>()
        }.otherwise {
            AccountManager.logout()
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
