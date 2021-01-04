package com.github.util

import android.app.Activity
import android.content.Intent
import android.os.Bundle

inline fun <reified T : Activity> Activity.launchActivity(block: Bundle.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    val bundle = Bundle()
    block(bundle)
    intent.putExtras(bundle)
    startActivity(intent)
}