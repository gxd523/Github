package com.github.util

import com.github.AppContext
import com.github.common.SpExt
import kotlin.reflect.jvm.jvmName

// TODO: 1/2/21 重点：泛型reified
inline fun <reified R, T> R.sp(default: T) = SpExt(AppContext, default, R::class.jvmName)