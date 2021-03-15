package com.rijksmuseum.masterpieces.features.common.models.loading

/**
 * Data to display on the Ui of an asynchronous data retrieval request.
 *
 * @param data  loaded data
 * @param load  a wrapper over the loading state
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