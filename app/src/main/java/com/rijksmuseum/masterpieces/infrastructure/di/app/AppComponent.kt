package com.rijksmuseum.masterpieces.infrastructure.di.app

import com.rijksmuseum.masterpieces.infrastructure.di.modules.NetworkModule
import com.rijksmuseum.masterpieces.infrastructure.di.modules.CollectionModule
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
