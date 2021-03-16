package com.rijksmuseum.masterpieces.services.collection.dto

import com.google.gson.annotations.SerializedName
import com.rijksmuseum.masterpieces.domain.ArtObjectBasics
import com.rijksmuseum.masterpieces.domain.ArtObjectDetailed
import com.rijksmuseum.masterpieces.infrastructure.network.Transformable

/**
 * DTO for a masterpiece details
 */
data class ArtObjectDetailedDto(
    @SerializedName("objectNumber")
    private val objectNumber: String,
    @SerializedName("title")
    private val title: String,
    @SerializedName("principalOrFirstMaker")
    private val principalOrFirstMaker: String,
    @SerializedName("webImage")
    private val image: ImageDto?,
    @SerializedName("dating")
    private val dating: DatingDto? = null,
    @SerializedName("description")
    private val description: String
) : Transformable<ArtObjectDetailed> {

    override fun transform(): ArtObjectDetailed {
        return ArtObjectDetailed(
            objectNumber,
            title,
            principalOrFirstMaker,
            image?.url,
            dating?.presentingDate ?: "",
            description
        )
    }
}