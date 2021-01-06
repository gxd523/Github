package com.github.common.delegate

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> delegateOf(
    getter: () -> T,
    setter: ((T) -> Unit)? = null,
    defaultValue: T? = null,
): ReadWriteProperty<Any, T> = ObjectPropertyDelegate0(getter, setter, defaultValue)

private class ObjectPropertyDelegate0<T>(
    val getter: () -> T,
    val setter: ((T) -> Unit)? = null,
    defaultValue: T? = null,
) : ReadWriteProperty<Any, T> {
    init {
        defaultValue?.let { setter?.invoke(it) }
    }

    override operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getter.invoke()
    }

    override operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        setter?.invoke(value)
    }
}

fun <T> delegateOf1(
    getter: (() -> T)? = null,
    setter: ((T) -> Unit)? = null,
    defaultValue: T? = null,
): ReadWriteProperty<Any, T> = ObjectPropertyDelegate1(getter, setter, defaultValue)

private class ObjectPropertyDelegate1<T>(
    private val getter: (() -> T)? = null,
    private val setter: ((T) -> Unit)? = null,
    defaultValue: T? = null,
) : ReadWriteProperty<Any, T> {
    private var value: T? = defaultValue

    init {
        defaultValue?.let { setter?.invoke(it) }
    }

    override operator fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getter?.invoke() ?: value!!
    }

    override operator fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        setter?.invoke(value)
        this.value = value
    }
}

fun <T, R> delegateLazyOf(
    getter: ((R) -> T)? = null,
    setter: ((R, T) -> Unit)? = null,
    defaultValue: T? = null,
    receiverBlock: () -> R,
): ReadWriteProperty<Any, T> = ObjectPropertyLazyDelegate(getter, setter, defaultValue, receiverBlock)

// TODO: 1/6/21 重点：代理使用
class ObjectPropertyLazyDelegate<T, R>(
    private val getter: ((R) -> T)? = null,
    private val setter: ((R, T) -> Unit)? = null,
    defaultValue: T? = null,
    receiverBlock: () -> R,
) : ReadWriteProperty<Any, T> {
    private var value: T? = defaultValue
    private val receiver by lazy {// TODO: 1/6/21 重点：懒初始化代理
        receiverBlock()
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getter?.invoke(receiver) ?: value!!
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        setter?.invoke(receiver, value)
        this.value = value
    }
}