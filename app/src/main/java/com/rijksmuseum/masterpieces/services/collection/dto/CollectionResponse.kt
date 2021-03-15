package com.rijksmuseum.masterpieces.services.collection.dto

import com.google.gson.annotations.SerializedName
import com.rijksmuseum.masterpieces.domain.ArtObjectBasics
import com.rijksmuseum.masterpieces.infrastructure.network.Transformable
import com.rijksmuseum.masterpieces.infrastructure.network.transformCollection

/**
 * DTO for masterpieces collection response
 */
data class CollectionResponse(
    @SerializedName("count")
    val totalItemsCount: Int,
    @SerializedName("artObjects")
    val artObjects: List<ArtObjectDto>
) : Transformable<List<ArtObjectBasics>> {

    override fun transform(): List<ArtObjectBasics> = artObjects.transformCollection()
}