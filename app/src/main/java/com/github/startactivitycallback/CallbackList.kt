package com.github.startactivitycallback

val startActivityCallbackList = emptyList<() -> Unit>().toMutableList()

fun findActivityCallback(hashcode: Int): (() -> Unit)? {
    for (callback in startActivityCallbackList) {
        if (hashcode == callback.hashCode()) {
            return callback
        }
    }
    return null
}