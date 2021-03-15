package com.rijksmuseum.masterpieces.features.details

import android.util.Log
import androidx.lifecycle.ViewModel
import javax.inject.Inject

interface MasterpieceDetailsViewModel {}

class MasterpieceDetailsViewModelImpl @Inject constructor(route: MasterpieceDetailsFragmentRoute) :
    MasterpieceDetailsViewModel, ViewModel() {

    init {
        Log.v("MasterpieceDetailsVM", "${hashCode()}")
    }
}