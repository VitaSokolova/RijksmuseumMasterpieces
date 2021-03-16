package com.rijksmuseum.masterpieces.features.common.models.loading

/**
 * Wrapper to display loading status on Ui.
 */
enum class Loading {
    /**
     * Loading state when there is no content on the screen yet
     */
    MAIN,

    /**
     * Loading state which invoked via pagination
     */
    PAGING;

    fun isMain(): Boolean = this == MAIN

    fun isPaging(): Boolean = this == PAGING
}

