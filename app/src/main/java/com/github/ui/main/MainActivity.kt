package com.github.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.github.R
import com.github.common.no
import com.github.common.otherwise
import com.github.common.yes
import com.github.databinding.ActivityMainBinding
import com.github.model.account.AccountManager
import com.github.model.account.OnAccountStateChangeListener
import com.github.network.entities.User
import com.github.settings.Themer
import com.github.ui.login.LoginActivity
import com.github.ui.main.navigation.MenuItemData
import com.github.ui.main.navigation.NavigationController
import com.github.ui.main.navigation.afterClosed
import com.github.util.launchActivity
import com.github.util.launchUi
import com.github.util.showFragment
import com.github.widget.confirm
import kotlinx.android.synthetic.main.menu_item_daynight.view.*
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    val actionBarController by lazy {
        ActionBarController(binding.tabLayout)
    }

    private val navigationController by lazy {
        NavigationController(binding.navigationView, ::onNavigationItemSelected, ::onNavigationHeaderClicked)
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
        Themer.applyProperTheme(this)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        initNavigationView()

        AccountManager.onAccountStateChangeListeners += accountListener
    }

    private fun initNavigationView() {
        AccountManager.isLoggedIn().yes {
            navigationController.useLoginLayout()
        }.otherwise {
            navigationController.useNoLoginLayout()
        }
        navigationController.selectMenuItem()
    }

    private fun onNavigationItemSelected(menuItemData: MenuItemData) {
        binding.drawerLayout.afterClosed {
            showFragment(R.id.fragmentContainer, menuItemData.fragmentClass, menuItemData.arguements)
            title = menuItemData.title
        }
    }

    private fun onNavigationHeaderClicked() {
        AccountManager.isLoggedIn().no {
            launchActivity<LoginActivity>()
        }.otherwise {
            launchUi {
                confirm("提示", "确认要注销吗?").yes {
                    AccountManager.logout()
                }.otherwise {
                    toast("取消了")
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_actionbar, menu)
        menu.findItem(R.id.dayNight).actionView.dayNightSwitch.apply {
            isChecked = Themer.currentTheme() == Themer.ThemeMode.DAY

            setOnCheckedChangeListener { _, _ ->
                Themer.toggle(this@MainActivity)
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        AccountManager.onAccountStateChangeListeners -= accountListener
        super.onDestroy()
    }
}