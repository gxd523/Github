package com.github.retroapollo

import com.apollographql.apollo.ApolloClient
import com.github.retroapollo.util.RetroApolloUtil
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class RetroApollo private constructor(
    val apolloClient: ApolloClient,
    private val callAdapterFactoryList: List<CallAdapter.Factory>,
) {
    class Builder {
        private var apolloClient: ApolloClient? = null

        fun apolloClient(apolloClient: ApolloClient): Builder {
            this.apolloClient = apolloClient
            return this
        }

        private val callAdapterFactoryList = arrayListOf<CallAdapter.Factory>(ApolloCallAdapterFactory())

        fun addCallAdapterFactory(callAdapterFactory: CallAdapter.Factory): Builder {
            callAdapterFactoryList.add(callAdapterFactory)
            return this
        }

        fun build() = apolloClient?.let {
            RetroApollo(it, callAdapterFactoryList)
        } ?: throw IllegalStateException("ApolloClient cannot be null.")
    }

    private val serviceMethodCache = ConcurrentHashMap<Method, ApolloServiceMethod<*>>()

    fun <T : Any> createGraphQLService(serviceClass: KClass<T>): T {
        val serviceJavaClass = serviceClass.java
        RetroApolloUtil.validateServiceInterface(serviceJavaClass)
        return Proxy.newProxyInstance(serviceJavaClass.classLoader, arrayOf(serviceJavaClass),
            object : InvocationHandler {
                override fun invoke(proxy: Any, method: Method, args: Array<Any>?): Any {
                    if (method.declaringClass == Any::class.java) {
                        return method.invoke(this, args)
                    }

                    return loadServiceMethod(method)(args)// TODO: 1/9/21 重点：实现了invoke
                }
            }) as T
    }

    fun loadServiceMethod(method: Method): ApolloServiceMethod<*> {
        var serviceMethod = serviceMethodCache[method]
        if (serviceMethod == null) {
            synchronized(serviceMethodCache) {
                serviceMethod = serviceMethodCache[method] ?: ApolloServiceMethod.Builder(this, method)
                    .build()
                    .also {
                        serviceMethodCache[method] = it
                    }
            }
        }
        return serviceMethod!!
    }

    fun getCallAdapter(type: Type): CallAdapter<Any, Any>? {
        for (callAdapterFactory in callAdapterFactoryList) {
            val callAdapter = callAdapterFactory.get(type)
            return callAdapter as? CallAdapter<Any, Any> ?: continue
        }
        return null
    }
}