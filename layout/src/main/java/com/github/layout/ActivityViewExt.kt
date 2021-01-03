package com.github.layout

import android.app.Activity
import android.widget.Button
import android.widget.LinearLayout

// TODO: 1/3/21 重点：泛型扩展函数
inline fun <reified T : Activity> T.frameLayout(init: @DslViewMaker DslFrameLayout.() -> Unit) =
    DslFrameLayout(this).also(::setContentView).also(init)

inline fun <reified T : Activity> T.linerLayout(init: @DslViewMaker DslLinearLayout.() -> Unit) =
    DslLinearLayout(this).also(::setContentView).also(init)

inline fun <reified T : Activity> T.verticalLayout(init: @DslViewMaker DslLinearLayout.() -> Unit) =
    DslLinearLayout(this).also(::setContentView).apply {
        orientation = LinearLayout.VERTICAL
    }.also(init)

inline fun <reified T : Activity> T.button(init: @DslViewMaker Button.() -> Unit) =
    Button(this).also(::setContentView).also(init)
