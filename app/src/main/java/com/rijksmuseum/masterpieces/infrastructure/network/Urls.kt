package com.rijksmuseum.masterpieces.infrastructure.network

const val BASE_URL = "https://www.rijksmuseum.nl/api/"
const val MASTERPIECES_LIST_URL = "{language}/collection"
const val MASTERPIECE_DETAILS_URL = "$MASTERPIECES_LIST_URL/{id}"