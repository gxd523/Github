package com.github.settings

import android.app.Activity
import androidx.annotation.StyleRes
import com.github.R

object Themer {
    enum class ThemeMode(@StyleRes val normal: Int, @StyleRes val translucent: Int) {
        DAY(R.style.AppTheme, R.style.AppTheme_Translucent),
        NIGHT(R.style.AppTheme_Dark, R.style.AppTheme_Dark_Translucent)
    }

    fun applyProperTheme(activity: Activity, translucent: Boolean = false) {
        activity.setTheme(currentTheme().let { if (translucent) it.translucent else it.normal })
    }

    fun currentTheme() = ThemeMode.valueOf(Settings.themeMode)

    fun toggle(activity: Activity) {
        Settings.themeMode = when (currentTheme()) {
            ThemeMode.DAY -> ThemeMode.NIGHT.name
            ThemeMode.NIGHT -> ThemeMode.DAY.name
        }
        activity.recreate()// TODO: 1/5/21 好方法
    }
}