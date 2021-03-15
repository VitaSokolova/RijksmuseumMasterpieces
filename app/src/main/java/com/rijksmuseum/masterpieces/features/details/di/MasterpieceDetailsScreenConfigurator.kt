package com.rijksmuseum.masterpieces.features.details.di

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.rijksmuseum.masterpieces.app.App
import com.rijksmuseum.masterpieces.features.details.MasterpieceDetailsFragment
import com.rijksmuseum.masterpieces.features.details.MasterpieceDetailsFragmentRoute
import com.rijksmuseum.masterpieces.features.details.MasterpieceDetailsViewModel
import com.rijksmuseum.masterpieces.features.details.MasterpieceDetailsViewModelImpl
import com.rijksmuseum.masterpieces.infrastructure.di.app.AppComponent
import com.rijksmuseum.masterpieces.infrastructure.di.screen.ScreenScope
import com.rijksmuseum.masterpieces.infrastructure.di.screen.UniversalViewModelProviderFactory
import com.rijksmuseum.masterpieces.infrastructure.di.screen.ViewModelStoreModule
import com.rijksmuseum.masterpieces.utils.getCurrentLocale
import dagger.Component
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Provider


/**
 * Class with screen component and module,
 * responsible for building dependencies for [MasterpieceDetailsFragment] screen
 */
class MasterpieceDetailsScreenConfigurator {

    @ScreenScope
    @Component(
        dependencies = [AppComponent::class],
        modules = [
            MasterpieceDetailsModule::class,
            ViewModelStoreModule::class
        ]
    )
    internal interface MasterpieceDetailsComponent {
        fun inject(view: MasterpieceDetailsFragment)
    }

    @Module
    internal class MasterpieceDetailsModule(private val bundle: Bundle?) {

        @Provides
        fun provideLocale(context: Context): Locale {
            return context.getCurrentLocale()
        }

        @Provides
        fun provideRoute(): MasterpieceDetailsFragmentRoute {
            return MasterpieceDetailsFragmentRoute(bundle ?: Bundle())
        }

        @Provides
        fun provideViewModel(
            viewModelStore: ViewModelStore,
            provider: Provider<MasterpieceDetailsViewModelImpl>
        ): MasterpieceDetailsViewModel {
            return ViewModelProvider(
                viewModelStore,
                UniversalViewModelProviderFactory(provider)
            ).get(
                MasterpieceDetailsViewModelImpl::class.java
            )
        }
    }

    fun inject(fragment: MasterpieceDetailsFragment) {
        val activity = fragment.activity

        DaggerMasterpieceDetailsScreenConfigurator_MasterpieceDetailsComponent.builder()
            .appComponent((activity?.application as App).appComponent)
            .viewModelStoreModule(ViewModelStoreModule(fragment.viewModelStore))
            .masterpieceDetailsModule(MasterpieceDetailsModule(fragment.arguments))
            .build()
            .inject(fragment)
    }
}