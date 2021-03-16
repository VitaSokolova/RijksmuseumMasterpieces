package com.rijksmuseum.masterpieces.features.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rijksmuseum.masterpieces.domain.ArtObjectBasics
import com.rijksmuseum.masterpieces.features.common.SingleLiveEvent
import com.rijksmuseum.masterpieces.features.common.extensions.applyError
import com.rijksmuseum.masterpieces.features.common.extensions.applyLoading
import com.rijksmuseum.masterpieces.features.common.extensions.merge
import com.rijksmuseum.masterpieces.features.common.models.loading.RequestUi
import com.rijksmuseum.masterpieces.features.common.models.pagination.PaginationBundle
import com.rijksmuseum.masterpieces.infrastructure.logging.Logger
import com.rijksmuseum.masterpieces.infrastructure.schedulers.SchedulersProvider
import com.rijksmuseum.masterpieces.services.collection.CollectionInteractor
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import java.util.*
import javax.inject.Inject

typealias LoadableArtObjects = RequestUi<PaginationBundle<ArtObjectBasics>>

/**
 * Interface for [MasterpiecesListFragment] ViewModel
 */
interface MasterpiecesListViewModel {
    // state
    val artObjects: LiveData<LoadableArtObjects>

    // event
    val showErrorMsg: SingleLiveEvent<Unit>

    fun loadFirstPage()

    fun loadNextPage()
}

class MasterpiecesListViewModelImpl @Inject constructor(
    private val locale: Locale,
    private val schedulersProvider: SchedulersProvider,
    private val logger: Logger,
    private val collectionInteractor: CollectionInteractor
) : ViewModel(), MasterpiecesListViewModel {

    override val artObjects = MutableLiveData<LoadableArtObjects>()

    override val showErrorMsg: SingleLiveEvent<Unit> = SingleLiveEvent()

    private val disposables = CompositeDisposable()

    init {
        loadFirstPage()
    }

    override fun onCleared() {
        disposables.clear()
    }

    override fun loadFirstPage() {
        loadData(locale, FIRST_PAGE_INDEX, PAGE_SIZE)
    }

    override fun loadNextPage() {
        val isPageAlreadyLoading = artObjects.value?.isLoadingNewPage ?: false
        if (!isPageAlreadyLoading) {
            val previousPage = artObjects.value?.data?.list
            loadData(
                locale,
                previousPage?.nextPage ?: FIRST_PAGE_INDEX,
                previousPage?.pageSize ?: PAGE_SIZE
            )
        }
    }

    private fun loadData(
        locale: Locale,
        pageNumber: Int,
        pageSize: Int
    ) {
        disposables.add(
            collectionInteractor.getTopMasterpieces(locale, pageNumber, pageSize)
                .subscribeOn(schedulersProvider.worker())
                .observeOn(schedulersProvider.main())
                .doOnSubscribe { onLoadingStarted() }
                .subscribe(
                    ::onSuccess,
                    ::onError
                )
        )
    }

    private fun onLoadingStarted() {
        val previousPage = artObjects.value ?: RequestUi()
        artObjects.value = previousPage.applyLoading()
    }

    private fun onSuccess(newPage: DataList<ArtObjectBasics>) {
        val previousPage = artObjects.value?.data
        val mergedData = previousPage.merge(newPage)
        artObjects.value = RequestUi(mergedData)
    }

    private fun onError(error: Throwable) {
        val previousPage = artObjects.value ?: RequestUi()
        artObjects.value = previousPage.applyError(error)
        showErrorMsg.call()
        logger.e("${error.message}\n${error.stackTrace}")
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
        private const val PAGE_SIZE = 10
    }
}