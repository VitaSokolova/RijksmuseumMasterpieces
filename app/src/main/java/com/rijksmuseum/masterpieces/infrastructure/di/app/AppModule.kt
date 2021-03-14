package com.rijksmuseum.masterpieces.infrastructure.di.app

import android.app.Application
import android.content.Context
import com.rijksmuseum.masterpieces.infrastructure.SchedulersProvider
import com.rijksmuseum.masterpieces.infrastructure.SchedulersProviderImpl
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
    internal fun schedulersProvider(): SchedulersProvider = SchedulersProviderImpl()
}
