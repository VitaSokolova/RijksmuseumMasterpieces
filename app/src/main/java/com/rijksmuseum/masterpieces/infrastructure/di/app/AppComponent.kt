package com.rijksmuseum.masterpieces.infrastructure.di.app

import com.rijksmuseum.masterpieces.infrastructure.network.di.NetworkModule
import com.rijksmuseum.masterpieces.services.collection.di.CollectionModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        CollectionModule::class
    ]
)
interface AppComponent : AppProxyDependencies
