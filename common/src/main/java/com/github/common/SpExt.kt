package com.github.common

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SpExt<T>(context: Context, private val defaultValue: T, spName: String = "default") :
    ReadWriteProperty<Any?, T> {
    private val sp by lazy {
        context.getSharedPreferences(spName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSpValue(property.name)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putSpValue(property.name, value)
    }

    private fun putSpValue(key: String, value: T) {
        with(sp.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is Int -> putInt(key, value)
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                else -> throw IllegalArgumentException("UnSupported Type!!!")
            }.apply()
        }
    }

    private fun getSpValue(key: String): T {
        return when (defaultValue) {
            is Long -> sp.getLong(key, defaultValue)
            is Int -> sp.getInt(key, defaultValue)
            is String -> sp.getString(key, defaultValue)
            is Boolean -> sp.getBoolean(key, defaultValue)
            else -> throw IllegalArgumentException("UnSupported Type!!!")
        } as T
    }
}
