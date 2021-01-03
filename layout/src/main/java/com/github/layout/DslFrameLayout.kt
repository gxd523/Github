package com.github.layout

import android.content.Context
import android.view.View
import android.widget.FrameLayout

// TODO: 1/3/21 重点：1、约束泛型扩展函数在类内部使用；2、外层receiver：DslFrameLayout，内层receiver：T
class DslFrameLayout(context: Context) : FrameLayout(context), DslViewParent {
    fun <T : View> T.lparams(width: Int = WRAP_CONTENT, height: Int = WRAP_CONTENT, init: LayoutParams.() -> Unit) {
        layoutParams = LayoutParams(width, height).also(init)
    }
}