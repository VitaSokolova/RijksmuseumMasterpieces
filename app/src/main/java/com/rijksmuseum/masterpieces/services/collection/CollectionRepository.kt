package com.rijksmuseum.masterpieces.services.collection

import com.rijksmuseum.masterpieces.BuildConfig
import com.rijksmuseum.masterpieces.domain.ArtObject
import com.rijksmuseum.masterpieces.domain.PaginationMetadata
import com.rijksmuseum.masterpieces.infrastructure.network.toDataList
import com.rijksmuseum.masterpieces.services.collection.mappers.LanguageMapper
import com.rijksmuseum.masterpieces.services.collection.mappers.LocalizedPaintingType
import io.reactivex.rxjava3.core.Single
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class to get data about museum masterpieces from the network
 */
@Singleton
class CollectionRepository @Inject constructor(private val collectionApi: CollectionApi) {

    /**
     * Returns a specific page of works that are top pieces
     */
    fun getTopMasterpieces(
        locale: Locale,
        pageNumber: Int,
        pageSize: Int
    ): Single<DataList<ArtObject>> {
        val localeServerConst = LanguageMapper(locale)
        return collectionApi.getTopMasterpieces(
            language = localeServerConst,
            culture = localeServerConst,
            key = BuildConfig.API_KEY,
            pageNumber = pageNumber,
            pageSize = pageSize,
            sortType = SORT_TYPE,
            onlyTopPieces = true,
            onlyWithImages = true,
            type = LocalizedPaintingType(locale)
        ).map {
            val metadata = PaginationMetadata(pageNumber, pageSize, it.totalItemsCount)
            it.transform().toDataList(metadata)
        }
    }

    companion object {
        private const val SORT_TYPE = "artist"
    }
}