package com.github.retroapollo.rxjava

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.github.retroapollo.CallAdapter
import com.github.retroapollo.rxjava.RxReturnType.OBSERVABLE
import com.github.retroapollo.rxjava.RxReturnType.SINGLE
import com.github.retroapollo.util.RetroApolloUtil
import rx.Observable
import rx.Scheduler
import rx.Single
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

enum class RxReturnType {
    OBSERVABLE, SINGLE
}

class RxJavaCallAdapter<T>(
    private val rxReturnType: RxReturnType,
    private val dataType: Type,
    private val subscribeScheduler: Scheduler? = null,
    private val observableScheduler: Scheduler? = null,
) : CallAdapter<T, Any> {
    override fun responseType(): Type {
        return if (dataType is ParameterizedType) {
            RetroApolloUtil.getParameterUpperBound(0, dataType)
        } else {
            dataType
        }
    }

    override fun adapt(call: ApolloCall<T>): Any {
        val callFunc = CallExecuteOnSubscribe(call)
        var originalObservable = Observable.create(callFunc)
        originalObservable = subscribeScheduler?.let(originalObservable::subscribeOn) ?: originalObservable
        originalObservable = observableScheduler?.let(originalObservable::observeOn) ?: originalObservable

        val observable: Observable<*> =
            // Observable<Response<Data>>
            if (dataType is ParameterizedType) {
                originalObservable
            } else {
                originalObservable.map { it.data }
            }

        return when (rxReturnType) {
            OBSERVABLE -> observable
            SINGLE -> observable.toSingle()
        }
    }
}

class RxJavaCallAdapterFactory : CallAdapter.Factory() {

    private var subscribeScheduler: Scheduler? = null

    fun subscribeScheduler(scheduler: Scheduler): RxJavaCallAdapterFactory {
        subscribeScheduler = scheduler
        return this
    }

    private var observableScheduler: Scheduler? = null

    fun observableScheduler(scheduler: Scheduler): RxJavaCallAdapterFactory {
        observableScheduler = scheduler
        return this
    }

    override fun get(returnType: Type): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        val dataType = getParameterUpperBound(0, returnType as ParameterizedType)
        if (dataType is ParameterizedType) {
            val responseType = getRawType(dataType)
            if (responseType != Response::class.java) {
                return null
            }
        }
        val rxReturnType = when (rawType) {
            Single::class.java -> SINGLE
            Observable::class.java -> OBSERVABLE
            else -> null
        } ?: return null
        return RxJavaCallAdapter<Any>(rxReturnType, dataType, subscribeScheduler, observableScheduler)
    }
}