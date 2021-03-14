package com.rijksmuseum.masterpieces.domain

/**
 * Class which describes a piece of art
 *
 * @property objectNumber identificator
 * @property title name and optional short description
 * @property principalOrFirstMaker the author or principal
 * @property imageUri url to image
 */
data class ArtObject(
    val objectNumber: String,
    val title: String,
    val principalOrFirstMaker: String,
    val imageUri: String
)