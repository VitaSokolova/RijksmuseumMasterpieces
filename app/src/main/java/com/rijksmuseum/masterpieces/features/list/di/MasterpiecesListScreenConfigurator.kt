package com.rijksmuseum.masterpieces.features.list.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.rijksmuseum.masterpieces.app.App
import com.rijksmuseum.masterpieces.features.list.DaggerMasterpiecesListScreenConfigurator_MasterpiecesListComponent
import com.rijksmuseum.masterpieces.features.list.MasterpiecesListFragment
import com.rijksmuseum.masterpieces.features.list.MasterpiecesListViewModel
import com.rijksmuseum.masterpieces.features.list.MasterpiecesListViewModelImpl
import com.rijksmuseum.masterpieces.infrastructure.di.app.AppComponent
import com.rijksmuseum.masterpieces.infrastructure.di.screen.UniversalViewModelProviderFactory
import com.rijksmuseum.masterpieces.infrastructure.di.screen.ScreenScope
import com.rijksmuseum.masterpieces.infrastructure.di.screen.ViewModelStoreModule
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Provider

/**
 * Class with screen component and module,
 * responsible for building dependencies for [MasterpiecesListFragment] screen
 */
class MasterpiecesListScreenConfigurator {

    @ScreenScope
    @Component(
        dependencies = [AppComponent::class],
        modules = [
            MasterpiecesListModule::class,
            ViewModelStoreModule::class
        ]
    )
    internal interface MasterpiecesListComponent {
        fun inject(view: MasterpiecesListFragment)
    }

    @Module
    internal class MasterpiecesListModule {

        @Provides
        fun provideViewModel(
            viewModelStore: ViewModelStore,
            provider: Provider<MasterpiecesListViewModelImpl>
        ): MasterpiecesListViewModel {
            return ViewModelProvider(viewModelStore, UniversalViewModelProviderFactory(provider)).get(
                MasterpiecesListViewModelImpl::class.java
            )
        }
    }

    fun inject(fragment: MasterpiecesListFragment) {
        val activity = fragment.activity

        DaggerMasterpiecesListScreenConfigurator_MasterpiecesListComponent.builder()
            .appComponent((activity?.application as App).appComponent)
            .viewModelStoreModule(ViewModelStoreModule(fragment.viewModelStore))
            .masterpiecesListModule(MasterpiecesListModule())
            .build()
            .inject(fragment)
    }
}