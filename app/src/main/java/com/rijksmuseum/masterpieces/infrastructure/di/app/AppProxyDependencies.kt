package com.rijksmuseum.masterpieces.infrastructure.di.app

import android.content.Context
import com.rijksmuseum.masterpieces.infrastructure.SchedulersProvider
import com.rijksmuseum.masterpieces.services.collection.CollectionInteractor

/**
 * An interface which contains all available dependencies in the scope PerApplication
 * Should be used in subcomponents, which dependent on [AppComponent].
 */
interface AppProxyDependencies {

    fun context(): Context
    fun schedulersProvider(): SchedulersProvider

    // Features Interactors
    fun collectionInteractor(): CollectionInteractor
}
