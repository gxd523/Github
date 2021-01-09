package com.github.retroapollo

import com.apollographql.apollo.api.Query
import com.github.retroapollo.annotations.GraphQLQuery
import com.github.retroapollo.util.RetroApolloUtil
import com.github.retroapollo.util.error
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

class ApolloServiceMethod<T : Any> private constructor(
    private val retroApollo: RetroApollo,
    /**
     * 生成类的build():Builder
     */
    private val buildMethod: Method,
    /**
     * 生成类的内部类Builder的build():生成类
     */
    private val buildMethodOfBuilder: Method,
    /**
     * 生成类中设置参数的Method的集合
     */
    private val queryParamSetterList: List<Method>,
    private val callAdapter: CallAdapter<Any, T>,
) {
    class Builder(
        private val retroApollo: RetroApollo,
        /**
         * Api接口中定义的方法
         */
        method: Method,
    ) {
        private val callAdapter: CallAdapter<Any, Any>
        private val buildMethod: Method
        private val buildMethodOfBuilder: Method
        private val queryParamSetterList = ArrayList<Method>()

        init {
            val returnType = method.genericReturnType
            if (RetroApolloUtil.hasUnresolvableType(returnType)) {
                throw method.error("Method return type must not include a type variable or wildcard: %s", returnType)
            }
            if (returnType === Void.TYPE) {
                throw method.error("Service methods cannot return void.")
            }
            if (returnType !is ParameterizedType) {
                val name = (returnType as Class<*>).simpleName
                throw IllegalStateException("$name return type must be parameterized as $name<Foo> or $name<out Foo>")
            }

            callAdapter = retroApollo.getCallAdapter(returnType) ?: throw  IllegalStateException("$returnType is not supported.")

            // RepositoryIssueCountQuery.Data.class
            val dataType = callAdapter.responseType() as Class<*>

            // 匿名内部类对应的外部类，即RepositoryIssueCountQuery.Data对应的外部类RepositoryIssueCountQuery
            val enclosingClass = dataType.enclosingClass

            buildMethod = enclosingClass.getDeclaredMethod("builder")

            // 类中声明的内部类，即Builder、Data等等
            val declaredClasses = enclosingClass.declaredClasses
            val builderClass = declaredClasses.first { it.simpleName == "Builder" }

            method.parameterAnnotations
                .zip(method.parameterTypes)
                .mapTo(queryParamSetterList) { (first, second) ->
                    val annotation = first.first { it is GraphQLQuery } as GraphQLQuery
                    builderClass.getDeclaredMethod(annotation.value, second)// GraphQLQuery里的参数在生成类中会有对应方法，和参数名相同
                }

            buildMethodOfBuilder = builderClass.getDeclaredMethod("build")
        }

        fun build() = ApolloServiceMethod(
            retroApollo,
            buildMethod,
            buildMethodOfBuilder,
            queryParamSetterList,
            callAdapter
        )
    }

    operator fun invoke(args: Array<Any>?): T {
        val builder = buildMethod(null)
        args?.let {
            queryParamSetterList.zip(it).forEach { pair ->
                pair.first.invoke(builder, pair.second)
            }
        }

        // RepositoryIssueCountQuery.builder().owner(xxx).repo(xxx).build()
        return callAdapter.adapt(retroApollo.apolloClient.query(buildMethodOfBuilder.invoke(builder) as Query<*, Any, *>))
    }

}