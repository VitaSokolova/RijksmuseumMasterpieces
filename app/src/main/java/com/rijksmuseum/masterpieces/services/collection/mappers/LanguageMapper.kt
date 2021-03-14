package com.rijksmuseum.masterpieces.services.collection.mappers

import com.rijksmuseum.masterpieces.utils.isDutch
import java.util.*

/**
 * Maps locale to a server constant
 */
object LanguageMapper : Function1<Locale, String> {

    override fun invoke(locale: Locale): String {
        return if (locale.isDutch()) {
            "nl"
        } else {
            "en"
        }
    }
}