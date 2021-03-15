package com.rijksmuseum.masterpieces.features.details.models

import com.rijksmuseum.masterpieces.domain.ArtObjectBasics
import com.rijksmuseum.masterpieces.domain.ArtObjectDetailed
import com.rijksmuseum.masterpieces.features.common.models.loading.RequestUi

/**
 * UI model which describes state of [MasterpieceDetailsFragment]
 */
data class DetailsScreenState(
    val basics: ArtObjectBasics,
    val details: RequestUi<ArtObjectDetailed>
)