package com.github.widget

import android.app.AlertDialog
import android.content.Context
import com.github.R
import com.github.common.otherwise
import com.github.common.yes
import com.github.settings.Themer
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// TODO: 1/9/21 重点：异步的UI操作也可以使用协程同步化
suspend fun Context.confirm(title: String, msg: String = "") = suspendCoroutine<Boolean> { continuation ->
    AlertDialog.Builder(
        this,
        (Themer.currentTheme() == Themer.ThemeMode.DAY).yes { R.style.LightDialog }.otherwise { R.style.DarkDialog }
    ).setTitle(title)
        .setMessage(msg)
        .setPositiveButton("OK") { _, _ ->
            continuation.resume(true)
        }
        .setNegativeButton("Cancel") { _, _ ->
            continuation.resume(false)
        }
        .setOnCancelListener {
            continuation.resume(false)
        }
        .show()
}