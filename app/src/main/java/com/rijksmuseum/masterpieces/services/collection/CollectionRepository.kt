package com.rijksmuseum.masterpieces.services.collection

import com.rijksmuseum.masterpieces.BuildConfig
import com.rijksmuseum.masterpieces.domain.ArtObject
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
     *
     * @param locale app locale
     *
     * @param pageNumber number of page ( if starting count from "1").
     * Experimentally it turned out that the API sends the same 0 and 1st pages and,
     * contrary to the documentation, starts from 1
     *
     * @param pageSize count of elements in a page
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
            pageNumber = pageNumber ,
            pageSize = pageSize,
            sortType = SORT_TYPE,
            onlyTopPieces = true,
            onlyWithImages = true,
            type = LocalizedPaintingType(locale)
        ).map {
            it.transform().toDataList(
                pageNumber = pageNumber,
                pageSize = pageSize,
                totalItemsCount = it.totalItemsCount
            )
        }
    }

    companion object {
        private const val SORT_TYPE = "artist"
    }
}