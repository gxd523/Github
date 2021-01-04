package com.github.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle

inline fun <reified T : Activity> Activity.launchActivity(noinline block: (Bundle.() -> Unit)? = null) {
    val intent = Intent(this, T::class.java)
    if (block != null) {
        val bundle = Bundle()
        block(bundle)
        intent.putExtras(bundle)
    }
    startActivity(intent)
}