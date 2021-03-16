package com.rijksmuseum.masterpieces.app

import android.app.Application
import com.rijksmuseum.masterpieces.infrastructure.di.app.AppComponent
import com.rijksmuseum.masterpieces.infrastructure.di.app.AppModule
import com.rijksmuseum.masterpieces.infrastructure.di.app.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        // tag for logging errors
        const val ERROR_TAG = "Rijksmuseum"
    }
}

