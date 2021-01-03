package com.github.layout

import android.content.Context
import android.view.View
import android.widget.LinearLayout

class DslLinearLayout(context: Context) : LinearLayout(context), DslViewParent {
    fun <T : View> T.lparams(width: Int = WRAP_CONTENT, height: Int = WRAP_CONTENT, init: LayoutParams.() -> Unit) {
        layoutParams = LayoutParams(width, height).also(init)
    }
}