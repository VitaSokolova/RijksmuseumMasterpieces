package com.rijksmuseum.masterpieces.infrastructure.network

/**
 * Interface for DTO models, which can be transformed into domain models
 *  @param <T> domain model type
 */
interface Transformable<T> {

    fun transform(): T
}