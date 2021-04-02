package com.rijksmuseum.masterpieces.infrastructure.logging

import android.util.Log
import com.rijksmuseum.masterpieces.app.App
import com.rijksmuseum.masterpieces.infrastructure.logging.Logger
import javax.inject.Inject

/**
 * Android platform [Logger] implementation
 */
class LoggerImpl @Inject constructor(): Logger {

    override fun v(msg: String) {
        Log.v(App.ERROR_TAG, msg)
    }

    override fun e(msg: String) {
        Log.e(App.ERROR_TAG, msg)
    }
}