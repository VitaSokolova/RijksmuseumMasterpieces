package com.rijksmuseum.masterpieces.services.collection.dto

import com.google.gson.annotations.SerializedName
import com.rijksmuseum.masterpieces.domain.ArtObject
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
) : Transformable<List<ArtObject>> {

    override fun transform(): List<ArtObject> = artObjects.transformCollection()
}