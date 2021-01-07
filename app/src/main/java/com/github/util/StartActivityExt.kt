package com.github.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

// TODO: 1/4/21 重点：函数类型也可以是可空类型
inline fun <reified T : Activity> Context.launchActivity(
    pendingTransition: Pair<Int, Int>? = null,
    noinline block: (Bundle.() -> Unit)? = null,
) {
    val intent = Intent(this, T::class.java)
    if (block != null) {
        val bundle = Bundle()
        block(bundle)
        intent.putExtras(bundle)
    }
    if (this !is Activity) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
    if (pendingTransition != null && this is Activity) {
        val (enterAnim, exitAnim) = pendingTransition
        overridePendingTransition(enterAnim, exitAnim)
    }
}