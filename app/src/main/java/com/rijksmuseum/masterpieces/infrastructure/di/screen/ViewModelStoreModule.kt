package com.rijksmuseum.masterpieces.infrastructure.di.screen

import androidx.lifecycle.ViewModelStore
import dagger.Module
import dagger.Provides

@Module
class ViewModelStoreModule(
    private val viewModelStore: ViewModelStore
) {

    @Provides
    @ScreenScope
    fun providesViewModelStore(): ViewModelStore {
        return viewModelStore
    }
}