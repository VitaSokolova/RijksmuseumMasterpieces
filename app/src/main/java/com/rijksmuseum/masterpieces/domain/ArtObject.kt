package com.rijksmuseum.masterpieces.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Interface which describes a piece of art
 *
 * @property objectNumber identificator
 * @property title name and optional short description
 * @property principalOrFirstMaker the author or principal
 * @property imageUri url to image
 */
interface ArtObject {
    val objectNumber: String
    val title: String
    val principalOrFirstMaker: String
    val imageUri: String
}

/**
 * Class which contains only basic information about a piece of art
 */
@Parcelize
data class ArtObjectBasics(
    override val objectNumber: String,
    override val title: String,
    override val principalOrFirstMaker: String,
    override val imageUri: String
) : ArtObject, Parcelable

/**
 * Class which contains detailed info about a piece of art
 */
data class ArtObjectDetailed(
    override val objectNumber: String,
    override val title: String,
    override val principalOrFirstMaker: String,
    override val imageUri: String,
    val dating: String,
    val description: String
) : ArtObject