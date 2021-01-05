package com.github.util

import android.content.Context
import android.content.ContextWrapper
import android.content.res.TypedArray
import android.util.TypedValue
import android.view.ContextThemeWrapper
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes

val View.contextThemeWrapper: ContextThemeWrapper
    get() = context.contextThemeWrapper

val Context.contextThemeWrapper: ContextThemeWrapper
    get() = when (this) {
        is ContextThemeWrapper -> this
        is ContextWrapper -> baseContext.contextThemeWrapper
        else -> throw IllegalStateException("Context is not an Activity, can't get theme: $this")
    }

// TODO: 1/5/21 根据主题获取属性的值
@StyleRes
fun View.attrStyle(@AttrRes attrColor: Int): Int = contextThemeWrapper.attrStyle(attrColor)

@StyleRes
private fun ContextThemeWrapper.attrStyle(@AttrRes attrRes: Int): Int =
    attr(attrRes) {
        it.getResourceId(0, 0)
    }

private fun <R> ContextThemeWrapper.attr(@AttrRes attrRes: Int, block: (TypedArray) -> R): R {
    val typedValue = TypedValue()
    if (!theme.resolveAttribute(attrRes, typedValue, true)) throw IllegalArgumentException("$attrRes is not resolvable")
    val a = obtainStyledAttributes(typedValue.data, intArrayOf(attrRes))
    val result = block(a)
    a.recycle()
    return result
}