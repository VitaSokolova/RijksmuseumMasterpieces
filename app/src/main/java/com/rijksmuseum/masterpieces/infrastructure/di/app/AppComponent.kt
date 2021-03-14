package com.rijksmuseum.masterpieces.infrastructure.di.app

import com.rijksmuseum.masterpieces.infrastructure.network.di.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class
    ]
)
interface AppComponent : AppProxyDependencies
