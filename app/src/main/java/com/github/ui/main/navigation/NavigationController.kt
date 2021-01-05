package com.github.ui.main.navigation

import android.view.MenuItem
import com.github.R
import com.github.model.account.AccountManager
import com.github.network.entities.User
import com.github.settings.Settings
import com.github.util.loadWithGlide
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.nav_header_main.view.*

// TODO: 1/5/21 重点：如何抽离Controller
class NavigationController(
    private val navigationView: NavigationView,
    private val onNavigationItemSelected: (MenuItemData) -> Unit,
    private val onNavigationHeaderClicked: () -> Unit,
) : NavigationView.OnNavigationItemSelectedListener {

    init {
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationView.apply {
            Settings.selectMenuItemId = item.itemId
            val navItem = MenuItemData[item.itemId]
            onNavigationItemSelected(navItem)
        }
        return true
    }

    fun useLoginLayout() {
        navigationView.menu.clear()
        navigationView.inflateMenu(R.menu.activity_main_drawer)
        onUpdate(AccountManager.currentUser)
        selectMenuItem()
    }

    fun useNoLoginLayout() {
        navigationView.menu.clear()
        navigationView.inflateMenu(R.menu.activity_main_drawer_no_logged_in)
        onUpdate(AccountManager.currentUser)
        selectMenuItem()
    }

    private fun onUpdate(user: User?) {
        navigationView.doOnLayoutAvailable {
            navigationView.apply {
                usernameView.text = user?.login ?: "请登录"
                emailView.text = user?.email ?: "gxd523@gmail.com"
                if (user == null) {
                    avatarView.setImageResource(R.drawable.ic_github)
                } else {
                    avatarView.loadWithGlide(user.avatar_url, user.login.first())
                }

                navigationHeader.setOnClickListener() { onNavigationHeaderClicked() }
            }
        }
    }

//    private var currentMenuItemWrapper: MenuItemWrapper? = null

    fun selectMenuItem() {
        navigationView.doOnLayoutAvailable {
//            Log.d("gxd", "currentMenuItemWrapper = $currentMenuItemWrapper")
//            val menuItemId = currentMenuItemWrapper?.let { MenuItemWrapper[it] } ?: Settings.selectMenuItemId
            navigationView.selectItem(
                Settings.selectMenuItemId
                    .takeIf { navigationView.menu.findItem(it) != null } ?: let { Settings.defaultMenuItemId }
            )
        }
    }
}