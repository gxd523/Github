package com.github.layout

import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout

inline fun <reified T : ViewGroup> T.frameLayout(init: @DslViewMaker DslFrameLayout.() -> Unit) =
    DslFrameLayout(context).also(::addView).also(init)


inline fun <reified T : ViewGroup> T.linerLayout(init: @DslViewMaker DslLinearLayout.() -> Unit) =
    DslLinearLayout(context).also(::addView).also(init)

inline fun <reified T : ViewGroup> T.verticalLayout(init: @DslViewMaker DslLinearLayout.() -> Unit) =
    DslLinearLayout(context).also(::addView).apply {
        orientation = LinearLayout.VERTICAL
    }.also(init)

inline fun <reified T : ViewGroup> T.button(init: @DslViewMaker Button.() -> Unit) =
    Button(context).also(::addView).also(init)
