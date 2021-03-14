package com.rijksmuseum.masterpieces.infrastructure.network

import com.rijksmuseum.masterpieces.domain.PaginationMetadata
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList

/**
 * Transforms collection of [Transformable] elements
 */
fun <R, T : Transformable<R>> List<T>?.transformCollection(): List<R> {
    return this?.map { it.transform() } ?: emptyList()
}

/**
 * Transforms collection into [DataList] using [PaginationMetadata]
 * @return [DataList]
 */
fun <T> Collection<T>.toDataList(
    paginationData: PaginationMetadata
): DataList<T> {
    with(paginationData) {
        val totalPagesCount =
            totalItemsCount / pageSize + (if (totalItemsCount % pageSize == 0) 0 else 1)
        return DataList(
            this@toDataList,
            pageNumber,
            pageSize,
            totalItemsCount,
            totalPagesCount
        )
    }
}