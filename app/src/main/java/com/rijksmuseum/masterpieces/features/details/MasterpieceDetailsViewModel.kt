package com.rijksmuseum.masterpieces.features.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rijksmuseum.masterpieces.domain.ArtObjectDetailed
import com.rijksmuseum.masterpieces.features.common.SingleLiveEvent
import com.rijksmuseum.masterpieces.features.common.models.loading.Loading
import com.rijksmuseum.masterpieces.features.common.models.loading.RequestUi
import com.rijksmuseum.masterpieces.features.details.models.DetailsScreenState
import com.rijksmuseum.masterpieces.infrastructure.logging.Logger
import com.rijksmuseum.masterpieces.infrastructure.schedulers.SchedulersProvider
import com.rijksmuseum.masterpieces.services.collection.CollectionInteractor
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

/**
 * Interface for [MasterpieceDetailsFragment] ViewModel
 */
interface MasterpieceDetailsViewModel {

    // state
    val screenState: LiveData<DetailsScreenState>

    // events
    val showErrorMsg: SingleLiveEvent<Unit>

    // actions
    fun reloadData()
}

class MasterpieceDetailsViewModelImpl @Inject constructor(
    private val locale: Locale,
    private val route: MasterpieceDetailsFragmentRoute,
    private val schedulersProvider: SchedulersProvider,
    private val logger: Logger,
    private val collectionInteractor: CollectionInteractor
) : MasterpieceDetailsViewModel, ViewModel() {

    override val screenState = MutableLiveData(DetailsScreenState(route.artObject, RequestUi()))

    override val showErrorMsg: SingleLiveEvent<Unit> = SingleLiveEvent()

    private val disposables = CompositeDisposable()

    init {
        loadDetails(route.artObject.objectNumber, locale)
    }

    override fun onCleared() {
        disposables.clear()
    }

    override fun reloadData() {
        loadDetails(route.artObject.objectNumber, locale)
    }

    private fun loadDetails(id: String, locale: Locale) {
        disposables.add(
            collectionInteractor.getMasterpieceDetails(id, locale)
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
        screenState.value = screenState.value?.copy(
            details = RequestUi(load = Loading.MAIN)
        )
    }

    private fun onSuccess(data: ArtObjectDetailed) {
        screenState.value = screenState.value?.copy(
            details = RequestUi(data)
        )
    }

    private fun onError(error: Throwable) {
        screenState.value = screenState.value?.copy(
            details = RequestUi(error = error)
        )
        showErrorMsg.call()
        logger.e("${error.message}\n${error.stackTrace}")
    }
}