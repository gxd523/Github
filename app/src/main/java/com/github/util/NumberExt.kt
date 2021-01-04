package com.github.util

import kotlin.math.roundToInt

val Int.kilo
    get() = if (this > 700) {
        "${((this / 100f).roundToInt() / 10f)}k"
    } else {
        "$this"
    }