package com.github.navigation

import android.view.MenuItem
import com.github.R
import com.github.common.log.logger
import com.github.model.account.AccountManager
import com.github.network.entities.User
import com.github.settings.Settings
import com.github.util.loadWithGlide
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk15.listeners.onClick

// TODO: 1/5/21 重点：如何抽离Controller
class NavigationController(
    private val navigationView: NavigationView,
    private val onNavItemChanged: (MenuItemWrapper) -> Unit,
    private val onHeaderClick: () -> Unit,
) : NavigationView.OnNavigationItemSelectedListener {

    init {
        navigationView.setNavigationItemSelectedListener(this)
    }

    private var currentItemWrapper: MenuItemWrapper? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigationView.apply {
            Settings.lastPage = item.itemId
            val navItem = MenuItemWrapper[item.itemId]
            onNavItemChanged(navItem)
        }
        return true
    }

    fun useLoginLayout() {
        navigationView.menu.clear()
        navigationView.inflateMenu(R.menu.activity_main_drawer) //inflate new items.
        onUpdate(AccountManager.currentUser)
        selectProperItem()
    }

    fun useNoLoginLayout() {
        navigationView.menu.clear()
        navigationView.inflateMenu(R.menu.activity_main_drawer_no_logged_in) //inflate new items.
        onUpdate(AccountManager.currentUser)
        selectProperItem()
    }

    private fun onUpdate(user: User?) {
        navigationView.doOnLayoutAvailable {
            navigationView.apply {
                usernameView.text = user?.login ?: "请登录"
                emailView.text = user?.email ?: "gxd523@gmail.com"
                if (user == null) {
                    avatarView.imageResource = R.drawable.ic_github
                } else {
                    avatarView.loadWithGlide(user.avatar_url, user.login.first())
                }

                navigationHeader.onClick { onHeaderClick() }
            }
        }
    }

    fun selectProperItem() {
        navigationView.doOnLayoutAvailable {
            logger.debug("selectProperItem onLayout: $currentItemWrapper")
            ((currentItemWrapper?.let { MenuItemWrapper[it] } ?: Settings.lastPage)
                .takeIf { navigationView.menu.findItem(it) != null } ?: run { Settings.defaultPage })
                .let(navigationView::selectItem)
        }
    }
}