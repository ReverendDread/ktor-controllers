@file:Suppress("MatchingDeclarationName")

package com.korrit.kotlin.ktor.controllers

import com.korrit.kotlin.ktor.controllers.delegates.HeaderParamDelegate
import com.korrit.kotlin.ktor.controllers.delegates.PathParamDelegate
import com.korrit.kotlin.ktor.controllers.delegates.QueryParamDelegate
import io.ktor.util.reflect.*
import kotlin.reflect.KProperty
import kotlin.reflect.full.defaultType
import kotlin.reflect.full.starProjectedType

/**
 * Can be used to create delegates which infer default param name from property name.
 */
interface ParamsDelegateProvider<out R> {
    /**
     * Function called when creating property delegate.
     *
     * https://kotlinlang.org/docs/reference/delegated-properties.html#providing-a-delegate-since-11
     */
    operator fun provideDelegate(thisRef: Input<*>, property: KProperty<*>): R
}

/**
 * Gets value of path param.
 *
 * @param T type of the param
 * @param name explicit name of the param
 */
inline fun <reified T> Input<*>.path(name: String? = null) = object : ParamsDelegateProvider<PathParamDelegate<T>> {
    override operator fun provideDelegate(thisRef: Input<*>, property: KProperty<*>) = PathParamDelegate<T>(name ?: property.name, TypeInfo(T::class, T::class.java, T::class.starProjectedType), thisRef)
}

/**
 * Gets value of path param.
 *
 * @param T type of the param
 * @param name explicit name of the param
 * @param default default value in case parameter was not provided, implies parameter is not required
 */
inline fun <reified T> Input<*>.path(name: String? = null, default: T) = object : ParamsDelegateProvider<PathParamDelegate<T>> {
    override operator fun provideDelegate(thisRef: Input<*>, property: KProperty<*>) = PathParamDelegate(name ?: property.name, TypeInfo(T::class, T::class.java, T::class.starProjectedType), thisRef, default)
}

/**
 * Gets value of query param.
 *
 * @param T type of the param
 * @param name explicit name of the param
 */
inline fun <reified T> Input<*>.query(name: String? = null) = object : ParamsDelegateProvider<QueryParamDelegate<T>> {
    override operator fun provideDelegate(thisRef: Input<*>, property: KProperty<*>) = QueryParamDelegate<T>(name ?: property.name, TypeInfo(T::class, T::class.java, T::class.starProjectedType), thisRef)
}

/**
 * Gets value of query param.
 *
 * @param T type of the param
 * @param name explicit name of the param
 * @param default default value in case parameter was not provided, implies parameter is not required
 */
inline fun <reified T> Input<*>.query(name: String? = null, default: T) = object : ParamsDelegateProvider<QueryParamDelegate<T>> {
    override operator fun provideDelegate(thisRef: Input<*>, property: KProperty<*>) = QueryParamDelegate(name ?: property.name, TypeInfo(T::class, T::class.java, T::class.starProjectedType), thisRef, default)
}

/**
 * Gets value of header param.
 *
 * @param T type of the param
 * @param name name of the header
 */
inline fun <reified T> Input<*>.header(name: String) = HeaderParamDelegate<T>(name, TypeInfo(T::class, T::class.java, T::class.starProjectedType), this)

/**
 * Gets value of header param.
 *
 * @param T type of the param
 * @param name name of the header
 * @param default default value in case parameter was not provided, implies parameter is not required
 */
inline fun <reified T> Input<*>.header(name: String, default: T) = HeaderParamDelegate(name, TypeInfo(T::class, T::class.java, T::class.starProjectedType), default, this)
