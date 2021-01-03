package com.github.layout

// TODO: 1/2/21 重点：DslMaker：保证外部作用域的receiver不会被隐式调用
@DslMarker
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE, AnnotationTarget.TYPEALIAS)
annotation class DslViewMaker

@DslViewMaker
interface DslViewParent

const val MATCH_PARENT = -1
const val WRAP_CONTENT = -2