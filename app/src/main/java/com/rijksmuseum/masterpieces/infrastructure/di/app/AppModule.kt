package com.rijksmuseum.masterpieces.infrastructure.di.app

import android.app.Application
import android.content.Context
import com.rijksmuseum.masterpieces.infrastructure.logging.Logger
import com.rijksmuseum.masterpieces.infrastructure.logging.LoggerImpl
import com.rijksmuseum.masterpieces.infrastructure.schedulers.SchedulersProvider
import com.rijksmuseum.masterpieces.infrastructure.schedulers.SchedulersProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(
    private val app: Application
) {

    @Provides
    @Singleton
    internal fun provideContext(): Context = app

    @Provides
    @Singleton
    internal fun provideApp(): Application = app

    @Provides
    @Singleton
    internal fun schedulersProvider(): SchedulersProvider =
        SchedulersProviderImpl()

    @Provides
    @Singleton
    internal fun Logger(): Logger =
        LoggerImpl()
}
