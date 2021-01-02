package com.github.common

// TODO: 1/2/21 重点：密封类+扩展函数+高阶函数
sealed class BooleanExt<out T>

object Otherwise : BooleanExt<Nothing>()
class WithData<T>(val data: T) : BooleanExt<T>()

inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T = when (this) {
    is Otherwise -> block()
    is WithData -> this.data
}

inline fun <T> Boolean.yes(block: () -> T): BooleanExt<T> = when {
    this -> WithData(block())
    else -> Otherwise
}

inline fun <T> Boolean.no(block: () -> T): BooleanExt<T> = when {
    this -> Otherwise
    else -> WithData(block())
}