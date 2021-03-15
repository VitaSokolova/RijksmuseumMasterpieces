package com.rijksmuseum.masterpieces.utils

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.ColorUtils
import com.rijksmuseum.masterpieces.R
import kotlin.math.roundToInt


/**
 * Returns the value for this attribute from a theme
 */
@ColorInt
fun getThemeAttrColor(
    context: Context,
    @AttrRes colorAttr: Int,
    @ColorRes defaultColor: Int = android.R.color.black
): Int {
    context.withStyledAttributes(null, intArrayOf(colorAttr)) {
        return getColor(0, ContextCompat.getColor(context, defaultColor))
    }
    return ContextCompat.getColor(context, defaultColor)
}

fun Toolbar.setDefaultIconTint(): Int {
    val defaultAttr = R.attr.colorOnSurface
    val defaultOpacity = 0.54
    return ColorUtils.setAlphaComponent(
        getThemeAttrColor(context, defaultAttr, android.R.color.black),
        (255 * defaultOpacity).roundToInt()
    )
}
