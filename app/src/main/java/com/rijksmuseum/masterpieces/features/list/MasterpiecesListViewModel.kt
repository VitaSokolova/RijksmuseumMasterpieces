package com.rijksmuseum.masterpieces.features.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rijksmuseum.masterpieces.domain.ArtObject
import com.rijksmuseum.masterpieces.features.common.models.loading.MainLoading
import com.rijksmuseum.masterpieces.features.common.models.loading.RequestUi
import com.rijksmuseum.masterpieces.features.common.models.pagination.PaginationBundle
import com.rijksmuseum.masterpieces.infrastructure.SchedulersProvider
import com.rijksmuseum.masterpieces.services.collection.CollectionInteractor
import com.rijksmuseum.masterpieces.utils.applyError
import com.rijksmuseum.masterpieces.utils.applyLoading
import com.rijksmuseum.masterpieces.utils.merge
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

typealias ArtObjects = RequestUi<PaginationBundle<ArtObject>>

/**
 * Interface for [MasterpiecesListFragment] ViewModel
 */
interface MasterpiecesListViewModel {

    val artObjects: LiveData<ArtObjects>

    fun loadFirstPage()

    fun loadNextPage()
}

class MasterpiecesListViewModelImpl @Inject constructor(
    private val locale: Locale,
    private val schedulersProvider: SchedulersProvider,
    private val collectionInteractor: CollectionInteractor
) : ViewModel(), MasterpiecesListViewModel {

    override val artObjects = MutableLiveData<ArtObjects>()

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
        val previousDataList = artObjects.value?.data?.list
        loadData(
            locale,
            previousDataList?.nextPage ?: FIRST_PAGE_INDEX,
            previousDataList?.pageSize ?: PAGE_SIZE
        )
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
                .doOnSubscribe {
                    val previousPage = artObjects.value ?: RequestUi()
                    // you can send another parameter, if swr loading is available too
                    artObjects.value = previousPage.applyLoading(MainLoading(true))
                }
                .subscribe(
                    { newPageDataList ->
                        val previousPage = artObjects.value?.data
                        artObjects.value = RequestUi(
                            previousPage.merge(newPageDataList)
                        )
                    },
                    { error ->
                        val previousPage = artObjects.value ?: RequestUi()
                        artObjects.value = previousPage.applyError(error)
                    }
                )
        )
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
        private const val PAGE_SIZE = 10
    }
}