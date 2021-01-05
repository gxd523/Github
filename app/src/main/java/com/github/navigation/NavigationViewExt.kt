package com.github.navigation

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.view.menu.MenuItemImpl
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.github.common.log.logger
import com.github.common.otherwise
import com.github.common.yes
import com.google.android.material.navigation.NavigationView

// TODO: 1/2/21 重点：扩展函数+高阶函数
inline fun NavigationView.doOnLayoutAvailable(crossinline block: () -> Unit) {
    ViewCompat.isLaidOut(this).yes {
        block()
    }.otherwise {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int,
            ) {
                removeOnLayoutChangeListener(this)
                block()
            }
        })
    }
}

/**
 * 选择指定的菜单，并执行相应的操作
 */
@SuppressLint("RestrictedApi")
fun NavigationView.selectItem(@IdRes resId: Int) {
    doOnLayoutAvailable {
        logger.debug("select Item: ${MenuItemWrapper[resId].title}")
        setCheckedItem(resId)
        (menu.findItem(resId) as MenuItemImpl)()
    }
}

inline fun DrawerLayout.afterClosed(crossinline block: () -> Unit) {
    if (isDrawerOpen(GravityCompat.START)) {
        closeDrawer(GravityCompat.START)
        addDrawerListener(
            object : DrawerLayout.DrawerListener {
                override fun onDrawerStateChanged(newState: Int) = Unit
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) = Unit
                override fun onDrawerOpened(drawerView: View) = Unit

                override fun onDrawerClosed(drawerView: View) {
                    removeDrawerListener(this)
                    block()
                }
            })
    } else {
        block()
    }
}
