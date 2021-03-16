package com.rijksmuseum.masterpieces.features.common.extensions

import com.rijksmuseum.masterpieces.features.common.models.loading.Loading
import com.rijksmuseum.masterpieces.features.common.models.loading.RequestUi
import com.rijksmuseum.masterpieces.features.common.models.pagination.PaginationBundle
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.easyadapter.pagination.PaginationState

/**
 * Helper method which applies successfully received data to the previous pages
 */
fun <T> PaginationBundle<T>?.merge(nextPage: DataList<T>): PaginationBundle<T> {
    val mergedPageData = this?.list?.merge(nextPage) ?: nextPage
    val newPaginationState = if (mergedPageData.canGetMore()) {
        PaginationState.READY
    } else {
        PaginationState.COMPLETE
    }
    return PaginationBundle(mergedPageData, newPaginationState)
}

/**
 * Helper method which applies received error to previous state
 *
 * If it is only a pagination error, we change only PaginationState
 * If an error occurred during the first page loading, we set a global error state
 */
fun <T> RequestUi<PaginationBundle<T>>.applyError(error: Throwable): RequestUi<PaginationBundle<T>> {
    return if (data?.hasData == true) {
        copy(data = data.copy(state = PaginationState.ERROR), load = null)
    } else {
        RequestUi(error = error, load = null)
    }
}

/**
 * Helper method which applies loading to previous state
 *
 * If it is only a pagination loading, we don't change anything
 * If it is the first page loading, we set a global loading state
 */
fun <T> RequestUi<PaginationBundle<T>>.applyLoading(): RequestUi<PaginationBundle<T>> {
    return if (data?.hasData == true) {
        copy(load = Loading.PAGING, error = null)
    } else {
        RequestUi(load = Loading.MAIN, error = null)
    }
}