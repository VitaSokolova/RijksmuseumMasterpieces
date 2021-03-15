package com.rijksmuseum.masterpieces.services.collection.dto

import com.google.gson.annotations.SerializedName
import com.rijksmuseum.masterpieces.domain.ArtObjectBasics
import com.rijksmuseum.masterpieces.infrastructure.network.Transformable

data class ArtObjectDto(
    @SerializedName("objectNumber")
    private val objectNumber: String,
    @SerializedName("title")
    private val title: String,
    @SerializedName("principalOrFirstMaker")
    private val principalOrFirstMaker: String,
    @SerializedName("webImage")
    private val image: ImageDto
) : Transformable<ArtObjectBasics> {

    override fun transform(): ArtObjectBasics {
        return ArtObjectBasics(objectNumber, title, principalOrFirstMaker, image.url)
    }
}