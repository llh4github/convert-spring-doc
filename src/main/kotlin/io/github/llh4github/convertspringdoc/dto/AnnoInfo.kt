package io.github.llh4github.convertspringdoc.dto

import kotlin.reflect.KClass

/**
 *
 *
 * Created At 2023/12/15 16:50
 * @author llh
 */
abstract class AnnoInfo<T : Annotation> {

    abstract val anno: KClass<T>

    abstract val defaultPropertyName: String

    val annoName: String by lazy {
        anno.simpleName!!
    }

    val annoPkgName: String by lazy {
        anno::class.java.packageName
    }
}

abstract class Sw2AnnoInfo<T : Annotation> : AnnoInfo<T>(), Swagger2Anno
abstract class Sw3AnnoInfo<T : Annotation> : AnnoInfo<T>(), Swagger3Anno
