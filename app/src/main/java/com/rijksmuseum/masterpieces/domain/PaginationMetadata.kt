package com.rijksmuseum.masterpieces.domain

/**
 * Describes pagination state
 *
 * @property pageNumber index of current page
 * @property pageSize elements on page count
 * @property totalItemsCount count of elements server can offer by this request
 */
data class PaginationMetadata(
    val pageNumber: Int = 0,
    val pageSize: Int = 0,
    val totalItemsCount: Int = 0
)