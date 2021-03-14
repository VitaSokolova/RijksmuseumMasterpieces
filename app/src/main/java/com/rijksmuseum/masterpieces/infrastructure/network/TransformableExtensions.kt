package com.rijksmuseum.masterpieces.infrastructure.network

/**
 * Transforms collection of [Transformable] elements
 */
fun <R, T : Transformable<R>> List<T>?.transformCollection(): List<R> {
    return this?.map { it.transform() } ?: emptyList()
}
