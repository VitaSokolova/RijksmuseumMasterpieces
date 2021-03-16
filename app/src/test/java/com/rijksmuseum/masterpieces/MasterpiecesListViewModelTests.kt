package com.rijksmuseum.masterpieces

import androidx.lifecycle.Observer
import com.rijksmuseum.masterpieces.common.BaseViewModelTests
import com.rijksmuseum.masterpieces.domain.ArtObjectBasics
import com.rijksmuseum.masterpieces.features.common.models.loading.Loading
import com.rijksmuseum.masterpieces.features.common.models.loading.RequestUi
import com.rijksmuseum.masterpieces.features.common.models.pagination.PaginationBundle
import com.rijksmuseum.masterpieces.features.list.LoadableArtObjects
import com.rijksmuseum.masterpieces.features.list.MasterpiecesListViewModelImpl
import com.rijksmuseum.masterpieces.infrastructure.network.toDataList
import com.rijksmuseum.masterpieces.services.collection.CollectionInteractor
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import java.util.*

/**
 * [com.rijksmuseum.masterpieces.features.list.MasterpiecesListViewModel] logic unit tests
 */
class MasterpiecesListViewModelTests : BaseViewModelTests() {

    private val mockLocale = Locale.ENGLISH
    private val mockCollectionInteractor = mockk<CollectionInteractor>()

    private lateinit var viewModel: MasterpiecesListViewModelImpl

    @Before
    fun setUp() {
        // because ViewModel has the request in constructor
        every {
            mockCollectionInteractor.getTopMasterpieces(any(), any(), any())
        } returns Single.just(DataList.empty())

        viewModel = MasterpiecesListViewModelImpl(
            mockLocale,
            mockSchedulers,
            mockLogger,
            mockCollectionInteractor
        )

        viewModel.artObjects.value = RequestUi()

        clearMocks(mockCollectionInteractor)
    }

    @Test
    fun `when first page is successfully loaded should show data`() {
        // Given
        every {
            mockCollectionInteractor.getTopMasterpieces(any(), any(), any())
        } returns Single.just(DataList.empty())

        val observer = mockk<Observer<LoadableArtObjects>>(relaxed = true)
        viewModel.artObjects.observeForever(observer)

        // When
        viewModel.loadFirstPage()

        // Then
        val slots = mutableListOf<LoadableArtObjects>()
        verify { observer.onChanged(capture(slots)) }

        assertSoftly {
            //skip initial LiveData value
            slots[1].load shouldBe Loading.MAIN
            slots[2].hasData shouldBe true
        }

        viewModel.artObjects.removeObserver(observer)
    }

    @Test
    fun `when first page loading finished with error should show placeholder`() {
        // Given
        val error = Throwable("Some mock error")
        every {
            mockCollectionInteractor.getTopMasterpieces(any(), any(), any())
        } returns Single.error(error)

        val observer = mockk<Observer<LoadableArtObjects>>(relaxed = true)
        viewModel.artObjects.observeForever(observer)

        // When
        viewModel.loadFirstPage()

        // Then
        val slots = mutableListOf<LoadableArtObjects>()
        verify { observer.onChanged(capture(slots)) }

        assertSoftly {
            //skip initial LiveData value
            slots[1].load shouldBe Loading.MAIN
            slots[2].error shouldBe error
            slots[2].hasError shouldBe true
        }

        viewModel.artObjects.removeObserver(observer)
    }

    @Test
    fun `when first page loading finished with error should show snack`() {
        // Given
        every {
            mockCollectionInteractor.getTopMasterpieces(any(), any(), any())
        } returns Single.error(Throwable("Some mock error"))

        val observer = mockk<Observer<Unit>>(relaxed = true)
        viewModel.showErrorMsg.observeForever(observer)

        // When
        viewModel.loadFirstPage()

        // Then
        verify { observer.onChanged(null) }

        viewModel.showErrorMsg.removeObserver(observer)
    }

    @Test
    fun `when next page loading finished with error should show snack`() {
        // Given
        every {
            mockCollectionInteractor.getTopMasterpieces(any(), any(), any())
        } returns Single.error(Throwable("Some mock error"))

        val observer = mockk<Observer<Unit>>(relaxed = true)
        viewModel.showErrorMsg.observeForever(observer)

        // When
        viewModel.loadNextPage()

        // Then
        verify { observer.onChanged(null) }

        viewModel.showErrorMsg.removeObserver(observer)
    }

    @Test
    fun `when next page is successful loaded should merged data`() {
        // Given
        val firstPage = createMockPage(listOf("1", "2"), 1)
        val nextPage = createMockPage(listOf("3", "4"), 2)
        val result = firstPage.merge(nextPage)

        every {
            mockCollectionInteractor.getTopMasterpieces(any(), any(), any())
        } returns Single.just(nextPage)

        viewModel.artObjects.value = RequestUi(PaginationBundle(firstPage, PaginationState.READY))

        val observer = mockk<Observer<LoadableArtObjects>>(relaxed = true)
        viewModel.artObjects.observeForever(observer)

        // When
        viewModel.loadNextPage()

        // Then
        val slots = mutableListOf<LoadableArtObjects>()
        verify { observer.onChanged(capture(slots)) }

        assertSoftly {
            //skip initial LiveData value
            slots[1].load shouldBe Loading.PAGING
            slots[2].data?.list shouldBe result
        }

        viewModel.artObjects.removeObserver(observer)
    }

    @Test
    fun `when last page is received should stop paging`() {
        // Given
        val firstPage =
            createMockPage(listOf("1", "2"), pageNumber = 1, pageSize = 2, totalItemsCount = 4)
        val lastPage =
            createMockPage(listOf("3"), pageNumber = 2, pageSize = 2, totalItemsCount = 4)
        val result = firstPage.merge(lastPage)

        every {
            mockCollectionInteractor.getTopMasterpieces(any(), any(), any())
        } returns Single.just(lastPage)

        viewModel.artObjects.value = RequestUi(PaginationBundle(firstPage, PaginationState.READY))

        val observer = mockk<Observer<LoadableArtObjects>>(relaxed = true)
        viewModel.artObjects.observeForever(observer)

        // When
        viewModel.loadNextPage()

        // Then
        val slots = mutableListOf<LoadableArtObjects>()
        verify { observer.onChanged(capture(slots)) }

        assertSoftly {
            //skip initial LiveData value
            slots[1].load shouldBe Loading.PAGING
            slots[2].data?.list shouldBe result
            slots[2].data?.state shouldBe PaginationState.COMPLETE
        }

        viewModel.artObjects.removeObserver(observer)
    }


    private fun createMockArtObject(id: String): ArtObjectBasics {
        return ArtObjectBasics(id, "Title", "Author", "https://mewmew.com")
    }

    private fun createMockPage(
        ids: List<String>,
        pageNumber: Int,
        pageSize: Int = ids.count(),
        totalItemsCount: Int = DataList.UNSPECIFIED_TOTAL_ITEMS_COUNT
    ): DataList<ArtObjectBasics> {
        return ids.map { createMockArtObject(it) }.toDataList(
            pageNumber = pageNumber,
            pageSize = pageSize,
            totalItemsCount = totalItemsCount
        )
    }
}