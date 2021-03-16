package com.rijksmuseum.masterpieces.infrastructure.di.modules

import com.rijksmuseum.masterpieces.services.collection.CollectionApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class CollectionModule {

    @Provides
    @Singleton
    fun provideCollectionApi(retrofit: Retrofit): CollectionApi {
        return retrofit.create(CollectionApi::class.java)
    }
}