package com.github.common.anno

// TODO: 1/3/21 重点：配合noArg(给Data Class添加无参构造函数)、allOpen(去掉Data Class的final修饰符，使之可以被继承)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class PoKo