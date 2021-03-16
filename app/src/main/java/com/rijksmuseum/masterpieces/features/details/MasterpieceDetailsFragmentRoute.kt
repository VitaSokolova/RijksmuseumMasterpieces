package com.rijksmuseum.masterpieces.features.details

import android.os.Bundle
import com.rijksmuseum.masterpieces.domain.ArtObjectBasics

/**
 * Class is responsible for working with input parameters of [MasterpieceDetailsFragment]
 */
class MasterpieceDetailsFragmentRoute(val artObject: ArtObjectBasics) {

    constructor(bundle: Bundle) : this(
        bundle.getParcelable<ArtObjectBasics>(EXTRA_ART_OBJECT)
            ?: error("Required input parameter ArtObject wasn't passed")
    )

    fun getBundle(): Bundle = Bundle().apply {
        putParcelable(EXTRA_ART_OBJECT, artObject)
    }

    companion object {
        private const val EXTRA_ART_OBJECT = "EXTRA_ART_OBJECT"
    }
}