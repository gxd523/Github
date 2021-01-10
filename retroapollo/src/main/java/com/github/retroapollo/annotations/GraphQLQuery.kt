package com.github.retroapollo.annotations

import kotlin.annotation.AnnotationRetention.RUNTIME

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(RUNTIME)
annotation class GraphQlQuery(val value: String)
