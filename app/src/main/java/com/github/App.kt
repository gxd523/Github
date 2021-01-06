package com.github

import android.app.Application
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.bennyhuo.swipefinishable.SwipeFinishable

private lateinit var INSTANCE: Application

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        SwipeFinishable.INSTANCE.init(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}

object AppContext : ContextWrapper(INSTANCE)