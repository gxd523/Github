package com.github.util

import android.view.View
import androidx.core.view.ViewCompat
import com.github.common.otherwise
import com.github.common.yes
import com.google.android.material.navigation.NavigationView

/**
 * Created by benny on 7/6/17.
 */
inline fun NavigationView.doOnLayoutAvailable(crossinline block: () -> Unit) {
    ViewCompat.isLaidOut(this).yes {
        block()
    }.otherwise {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                v: View?,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int,
            ) {
                removeOnLayoutChangeListener(this)
                block()
            }
        })
    }
}


