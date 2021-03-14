package com.rijksmuseum.masterpieces.features.common.models.loading

/**
 * Wrapper to display loading status on Ui.
 */
interface Loading {
    val isLoading: Boolean
}

/**
 * Loading state when there is no content on the screen yet
 */
class MainLoading(override val isLoading: Boolean) :
    Loading

/**
 * Loading state which invoked via SwipeRefresh
 */
class SwipeRefreshLoading(override val isLoading: Boolean) :
    Loading