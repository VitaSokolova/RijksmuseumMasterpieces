package com.rijksmuseum.masterpieces.services.collection.dto

import com.google.gson.annotations.SerializedName

data class DatingDto(
	@SerializedName("period")
	val period: Int? = null,
	@SerializedName("sortingDate")
	val sortingDate: Int? = null,
	@SerializedName("yearLate")
	val yearLate: Int? = null,
	@SerializedName("yearEarly")
	val yearEarly: Int? = null,
	@SerializedName("presentingDate")
	val presentingDate: String? = null
)
