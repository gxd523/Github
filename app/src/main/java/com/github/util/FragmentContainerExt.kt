package com.github.util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.showFragment(containerId: Int, clazz: Class<out Fragment>, vararg args: Pair<String, String>) {
    supportFragmentManager.beginTransaction()
        .replace(containerId,
            clazz.newInstance().apply { arguments = Bundle().apply { args.forEach { putString(it.first, it.second) } } },
            clazz.name)
        .commitAllowingStateLoss()
}

fun AppCompatActivity.showFragment(containerId: Int, clazz: Class<out Fragment>, args: Bundle? = null) {
    supportFragmentManager
        .beginTransaction()
        .replace(
            containerId,
            clazz.newInstance().apply { if (args != null) arguments = args },
            clazz.name
        )
        .commitAllowingStateLoss()
}