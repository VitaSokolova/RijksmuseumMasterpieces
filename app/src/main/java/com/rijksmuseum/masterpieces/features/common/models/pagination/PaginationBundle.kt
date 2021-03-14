package com.rijksmuseum.masterpieces.features.common.models.pagination

import com.rijksmuseum.masterpieces.features.common.models.loading.RequestUi
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.easyadapter.pagination.PaginationState

/**
 * A paginable list for displaying on the UI.
 */
data class PaginationBundle<T>(
    val list: DataList<T>? = null,
    val state: PaginationState? = null
) {

    val hasData = !list.isNullOrEmpty()
}