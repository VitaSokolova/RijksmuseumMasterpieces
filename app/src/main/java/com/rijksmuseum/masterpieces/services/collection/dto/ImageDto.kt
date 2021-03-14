package com.rijksmuseum.masterpieces.services.collection.dto

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("url")
    val url: String
)