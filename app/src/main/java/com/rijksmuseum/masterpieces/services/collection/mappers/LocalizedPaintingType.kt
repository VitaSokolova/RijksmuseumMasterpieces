package com.rijksmuseum.masterpieces.services.collection.mappers

import com.rijksmuseum.masterpieces.utils.isDutch
import java.util.*

/**
 * Returns correct constant for painting object type depending on locale
 */
object LocalizedPaintingType : Function1<Locale, String> {

    override fun invoke(locale: Locale): String {
        return if (locale.isDutch()) {
            "schilderij"
        } else {
            "painting"
        }
    }
}