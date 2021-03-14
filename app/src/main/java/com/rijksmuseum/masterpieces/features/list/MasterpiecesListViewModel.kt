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
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import java.util.*
import javax.inject.Inject

typealias ArtObjects = RequestUi<PaginationBundle<ArtObject>>

/**
 * Interface for [MasterpiecesListFragment] ViewModel
 */
interface MasterpiecesListViewModel {

    val artObjects: LiveData<ArtObjects>

    fun loadFirstPage(locale: Locale)
    fun loadNextPage(locale: Locale)
}

class MasterpiecesListViewModelImpl @Inject constructor(
    private val schedulersProvider: SchedulersProvider,
    private val collectionInteractor: CollectionInteractor
) : ViewModel(), MasterpiecesListViewModel {

    override val artObjects = MutableLiveData<ArtObjects>()

    private val disposables = CompositeDisposable()

    override fun onCleared() {
        disposables.clear()
    }

    override fun loadFirstPage(locale: Locale) {
        loadData(locale, 0, PAGE_SIZE)
    }

    override fun loadNextPage(locale: Locale) {
        val previousDataList = artObjects.value?.data?.list
        loadData(
            locale,
            previousDataList?.nextPage ?: 0,
            previousDataList?.pageSize ?: 0
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
                .doOnSubscribe { artObjects.value = RequestUi(load = MainLoading(true)) }
                .subscribe(
                    {
                        //todo: DataLists merging
                        artObjects.value = RequestUi(PaginationBundle(it, PaginationState.COMPLETE))
                    },
                    {
                        artObjects.value = RequestUi(error = it)
                    }
                )
        )
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}