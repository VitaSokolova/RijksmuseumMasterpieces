package com.rijksmuseum.masterpieces.services.collection

import com.rijksmuseum.masterpieces.domain.ArtObjectBasics
import com.rijksmuseum.masterpieces.domain.ArtObjectDetailed
import io.reactivex.rxjava3.core.Single
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class to get data about museum masterpieces
 * ("Use Case" in Clean architecture terminology)
 */
@Singleton
class CollectionInteractor @Inject constructor(
    private val collectionRepository: CollectionRepository
) {

    /**
     * Returns a specific page of works that are top pieces
     *
     * @param locale app locale
     * @param pageNumber number of page ( if starting count from "1")
     * @param pageSize count of elements in a page
     */
    fun getTopMasterpieces(
        locale: Locale,
        pageNumber: Int,
        pageSize: Int
    ): Single<DataList<ArtObjectBasics>> {
        return collectionRepository.getTopMasterpieces(locale, pageNumber, pageSize)
    }

    /**
     * Returns detailed information about selected masterpiece
     */
    fun getMasterpieceDetails(id: String, locale: Locale): Single<ArtObjectDetailed> {
        return collectionRepository.getMasterpieceDetails(id, locale)
    }
}