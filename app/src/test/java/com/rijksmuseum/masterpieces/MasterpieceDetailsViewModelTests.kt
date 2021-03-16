package com.rijksmuseum.masterpieces

import androidx.lifecycle.Observer
import com.rijksmuseum.masterpieces.common.BaseViewModelTests
import com.rijksmuseum.masterpieces.domain.ArtObjectBasics
import com.rijksmuseum.masterpieces.domain.ArtObjectDetailed
import com.rijksmuseum.masterpieces.features.common.models.loading.Loading
import com.rijksmuseum.masterpieces.features.common.models.loading.RequestUi
import com.rijksmuseum.masterpieces.features.details.MasterpieceDetailsFragmentRoute
import com.rijksmuseum.masterpieces.features.details.MasterpieceDetailsViewModelImpl
import com.rijksmuseum.masterpieces.features.details.models.DetailsScreenState
import com.rijksmuseum.masterpieces.services.collection.CollectionInteractor
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import java.util.*

/**
 * [com.rijksmuseum.masterpieces.features.details.MasterpieceDetailsViewModel] logic unit tests
 */
class MasterpieceDetailsViewModelTests : BaseViewModelTests() {

    private val mockLocale = Locale.ENGLISH
    private val mockCollectionInteractor = mockk<CollectionInteractor>(relaxed = true)
    private val mockInputParameter = ArtObjectBasics(
        "id",
        "Picture",
        "Author",
        "https://mewmew.com"
    )
    private val mockResponseData = ArtObjectDetailed(
        "id",
        "Picture",
        "Author",
        "https://mewmew.com",
        "2000-2003",
        "Description"
    )

    private lateinit var viewModel: MasterpieceDetailsViewModelImpl

    @Before
    fun setUp() {
        // because ViewModel has the request in constructor
        every {
            mockCollectionInteractor.getMasterpieceDetails(any(), any())
        } returns Single.just(mockResponseData)

        viewModel = MasterpieceDetailsViewModelImpl(
            mockLocale,
            MasterpieceDetailsFragmentRoute(mockInputParameter),
            mockSchedulers,
            mockLogger,
            mockCollectionInteractor
        )

        viewModel.screenState.value = DetailsScreenState(mockInputParameter, RequestUi())

        clearMocks(mockCollectionInteractor)
    }

    @Test
    fun `when data is successfully loaded should show data`() {
        // Given
        every {
            mockCollectionInteractor.getMasterpieceDetails(any(), any())
        } returns Single.just(mockResponseData)

        val observer = mockk<Observer<DetailsScreenState>>(relaxed = true)
        viewModel.screenState.observeForever(observer)

        // When
        viewModel.reloadData()

        // Then
        val slots = mutableListOf<DetailsScreenState>()
        verify { observer.onChanged(capture(slots)) }

        assertSoftly {
            //skip initial LiveData value
            slots[1].details.load shouldBe Loading.MAIN
            slots[2].details.data shouldBe mockResponseData
        }

        viewModel.screenState.removeObserver(observer)
    }

    @Test
    fun `when data loading finished with error should show placeholder`() {
        // Given
        val error = Throwable("Some mock error")
        every {
            mockCollectionInteractor.getMasterpieceDetails(any(), any())
        } returns Single.error(error)

        val observer = mockk<Observer<DetailsScreenState>>(relaxed = true)
        viewModel.screenState.observeForever(observer)

        // When
        viewModel.reloadData()

        // Then
        val slots = mutableListOf<DetailsScreenState>()
        verify { observer.onChanged(capture(slots)) }

        assertSoftly {
            //skip initial LiveData value
            slots[1].details.load shouldBe Loading.MAIN
            slots[2].details.error shouldBe error
            slots[2].details.hasError shouldBe true
        }

        viewModel.screenState.removeObserver(observer)
    }

    @Test
    fun `when loading finished with error should show snack`() {
        // Given
        every {
            mockCollectionInteractor.getMasterpieceDetails(any(), any())
        } returns Single.error(Throwable("Some mock error"))

        val observer = mockk<Observer<Unit>>(relaxed = true)
        viewModel.showErrorMsg.observeForever(observer)

        // When
        viewModel.reloadData()

        // Then
        verify { observer.onChanged(null) }

        viewModel.showErrorMsg.removeObserver(observer)
    }
}