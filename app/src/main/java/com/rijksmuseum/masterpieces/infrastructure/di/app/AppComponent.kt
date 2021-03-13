package com.rijksmuseum.masterpieces.infrastructure.di.app

import com.rijksmuseum.masterpieces.infrastructure.di.app.AppModule
import com.rijksmuseum.masterpieces.infrastructure.di.app.AppProxyDependencies
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class
    ]
)
interface AppComponent : AppProxyDependencies
