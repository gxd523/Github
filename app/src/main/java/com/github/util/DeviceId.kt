package com.github.util

import android.content.Context
import android.provider.Settings

// TODO: 1/2/21 重点：扩展属性
val Context.deviceId: String
    get() = Settings.Secure.getString(
        contentResolver,
        Settings.Secure.ANDROID_ID
    )