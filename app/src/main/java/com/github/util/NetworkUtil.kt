package com.github.util

import android.content.Context
import android.net.ConnectivityManager
import com.github.AppContext

object NetworkUtil {
    fun isAvailable(): Boolean {
        val connectivityManager = AppContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isAvailable ?: false
    }
}