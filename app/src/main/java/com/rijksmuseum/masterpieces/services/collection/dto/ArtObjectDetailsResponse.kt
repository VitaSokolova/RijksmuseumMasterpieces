package com.rijksmuseum.masterpieces.services.collection.dto

import com.google.gson.annotations.SerializedName
import com.rijksmuseum.masterpieces.domain.ArtObjectDetailed
import com.rijksmuseum.masterpieces.infrastructure.network.Transformable

/**
 * DTO for a masterpiece details response
 */
data class ArtObjectDetailsResponse(
    @SerializedName("artObject")
    val artObject: ArtObjectDetailedDto
) : Transformable<ArtObjectDetailed> {

    override fun transform(): ArtObjectDetailed = artObject.transform()
}