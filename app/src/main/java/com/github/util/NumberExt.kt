package com.github.util

import kotlin.math.roundToInt

/**
 * Created by benny on 7/9/17.
 */
fun Int.toKilo(): String {
    return if (this > 700) {
        "${((this / 100f).roundToInt() / 10f)}k"
    } else {
        "$this"
    }
}