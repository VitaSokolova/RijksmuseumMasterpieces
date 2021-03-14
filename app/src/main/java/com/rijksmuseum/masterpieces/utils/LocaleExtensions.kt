package com.rijksmuseum.masterpieces.utils

import java.util.*

fun Locale.isDutch(): Boolean {
    return language == Locale("nl").language
}