package com.rijksmuseum.masterpieces.utils

import android.content.Context
import android.os.Build
import java.util.*


fun Locale.isDutch(): Boolean {
    return language == Locale("nl").language
}

fun Context?.getCurrentLocale(): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this?.resources?.configuration?.locales?.get(0)
    } else {
        this?.resources?.configuration?.locale
    } ?: Locale.getDefault()
}