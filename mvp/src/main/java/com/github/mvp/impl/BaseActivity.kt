package com.github.mvp.impl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mvp.IPresenter
import com.github.mvp.IViewer
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

abstract class BaseActivity<out Presenter : BasePresenter<BaseActivity<Presenter>>> : AppCompatActivity(), IViewer<Presenter> {
    final override val presenter: Presenter

    init {
        presenter = createPresenter()
        presenter.viewer = this
    }

    private fun createPresenter(): Presenter {
        sequence<Type> {
            var thisClass: Class<*> = this@BaseActivity.javaClass
            while (true) {
                yield(thisClass.genericSuperclass)
                thisClass = thisClass.superclass ?: break
            }
        }.filter {
            it is ParameterizedType
        }.map {
            it as ParameterizedType
        }.flatMap {
            it.actualTypeArguments.asSequence()
        }.first {
            it is Class<*> && IPresenter::class.java.isAssignableFrom(it)
        }.let {
            return (it as Class<Presenter>).newInstance()
        }
    }

    private fun createPresenterKt(): Presenter {
        sequence {
            var thisClass: KClass<*> = this@BaseActivity::class
            while (true) {
                yield(thisClass.supertypes)
                thisClass = thisClass.supertypes.firstOrNull()?.jvmErasure ?: break
            }
        }.flatMap {
            it.flatMap { it.arguments }.asSequence()
        }.first {
            it.type?.jvmErasure?.isSubclassOf(IPresenter::class) ?: false
        }.let {
            return it.type!!.jvmErasure.primaryConstructor!!.call() as Presenter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {}

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        presenter.onViewStateRestored(savedInstanceState)
    }
}