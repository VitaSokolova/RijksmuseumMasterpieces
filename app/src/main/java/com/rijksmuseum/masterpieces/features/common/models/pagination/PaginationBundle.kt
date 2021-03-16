package com.rijksmuseum.masterpieces.features.common.models.pagination

import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.easyadapter.pagination.PaginationState

/**
 * A paginable list for displaying on the UI.
 *
 * @property list data
 * @property state shoes if next page loading available
 */
data class PaginationBundle<T>(
    val list: DataList<T>? = null,
    val state: PaginationState = PaginationState.COMPLETE
) {

    val hasData = !list.isNullOrEmpty()
}