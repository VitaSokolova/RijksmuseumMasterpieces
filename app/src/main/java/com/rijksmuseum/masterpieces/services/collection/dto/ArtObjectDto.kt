package com.rijksmuseum.masterpieces.services.collection.dto

import com.google.gson.annotations.SerializedName
import com.rijksmuseum.masterpieces.domain.ArtObject
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
) : Transformable<ArtObject> {

    override fun transform(): ArtObject {
        return ArtObject(objectNumber, title, principalOrFirstMaker, image.url)
    }
}