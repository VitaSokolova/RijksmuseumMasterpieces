package com.rijksmuseum.masterpieces.infrastructure.di.app

import com.rijksmuseum.masterpieces.infrastructure.di.modules.CollectionModule
import com.rijksmuseum.masterpieces.infrastructure.di.modules.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        CollectionModule::class,
        SchedulersModule::class
    ]
)
interface AppComponent : AppProxyDependencies
