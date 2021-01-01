package com.github

import android.app.Application
import android.content.ContextWrapper
import androidx.multidex.MultiDexApplication

private lateinit var INSTANCE: Application

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

object AppContext : ContextWrapper(INSTANCE)