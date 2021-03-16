package com.rijksmuseum.masterpieces.features.common.models.loading

/**
 * Data to display an asynchronous data retrieval request state on the UI
 *
 * @param data  loaded data
 * @param load  a type of loading state
 * @param error if it occurred during the request
 */
data class RequestUi<T>(
    val data: T? = null,
    val load: Loading? = null,
    val error: Throwable? = null
) {

    /**
     * A flag that determines whether the request is currently executing
     */
    val isLoading: Boolean get() = load != null

    /**
     * A flag that determines whether the paging request is currently executing
     */
    val isLoadingNewPage: Boolean get() = load?.isPaging() == true

    /**
     * A flag that determines if data is already loaded
     */
    val hasData: Boolean get() = data != null

    /**
     * A flag that determines if there is an error as a result of the request
     */
    val hasError: Boolean get() = error != null

}