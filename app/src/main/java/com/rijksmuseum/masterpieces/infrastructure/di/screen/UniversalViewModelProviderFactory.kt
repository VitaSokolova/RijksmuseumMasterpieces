package com.rijksmuseum.masterpieces.infrastructure.di.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

/**
 * Helper class used to create ViewModel in ScreenConfigurator
 */
class UniversalViewModelProviderFactory<T: ViewModel>(
    private val provider: Provider<T>
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return provider.get() as T
    }
}