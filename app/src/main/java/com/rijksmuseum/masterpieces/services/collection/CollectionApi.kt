package com.rijksmuseum.masterpieces.services.collection

import com.rijksmuseum.masterpieces.infrastructure.network.MASTERPIECES_LIST_URL
import com.rijksmuseum.masterpieces.infrastructure.network.MASTERPIECE_DETAILS_URL
import com.rijksmuseum.masterpieces.services.collection.dto.ArtObjectDetailedDto
import com.rijksmuseum.masterpieces.services.collection.dto.ArtObjectDetailsResponse
import com.rijksmuseum.masterpieces.services.collection.dto.CollectionResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface which describes related to art collection API methods
 */
interface CollectionApi {

    /**
     * Returns works that are top pieces
     */
    @GET(MASTERPIECES_LIST_URL)
    fun getTopMasterpieces(
        @Path("language") language: String,
        @Query("culture") culture: String,
        @Query("key") key: String,
        @Query("p") pageNumber: Int,
        @Query("ps") pageSize: Int,
        @Query("s") sortType: String,
        @Query("toppieces") onlyTopPieces: Boolean,
        @Query("imgonly") onlyWithImages: Boolean,
        @Query("type") type: String
    ): Single<CollectionResponse>

    @GET(MASTERPIECE_DETAILS_URL)
    fun getMasterpieceDetails(
        @Path("language") language: String,
        @Path("id") id: String,
        @Query("culture") culture: String,
        @Query("key") key: String
    ): Single<ArtObjectDetailsResponse>
}