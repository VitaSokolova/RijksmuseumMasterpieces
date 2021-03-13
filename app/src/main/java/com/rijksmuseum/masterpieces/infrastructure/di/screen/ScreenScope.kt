package com.rijksmuseum.masterpieces.infrastructure.di.screen

import javax.inject.Scope

/**
 * Custom scope to mark dependencies, which lifecycle is limited to a one screen (fragment)
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ScreenScope
