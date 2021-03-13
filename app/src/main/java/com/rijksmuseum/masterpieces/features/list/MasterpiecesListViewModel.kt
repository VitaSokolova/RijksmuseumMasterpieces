package com.rijksmuseum.masterpieces.features.list

import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Interface for [MasterpiecesListFragment] ViewModel
 */
interface MasterpiecesListViewModel

class MasterpiecesListViewModelImpl @Inject constructor() : ViewModel(), MasterpiecesListViewModel