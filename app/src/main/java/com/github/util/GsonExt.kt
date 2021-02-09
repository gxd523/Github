package com.github.util

import com.google.gson.Gson

// TODO: 1/2/21 重点：泛型reified
inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, T::class.java)