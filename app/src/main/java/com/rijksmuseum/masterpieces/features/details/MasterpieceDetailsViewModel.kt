package com.rijksmuseum.masterpieces.features.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rijksmuseum.masterpieces.features.common.models.loading.Loading
import com.rijksmuseum.masterpieces.features.common.models.loading.RequestUi
import com.rijksmuseum.masterpieces.features.details.models.DetailsScreenState
import com.rijksmuseum.masterpieces.infrastructure.SchedulersProvider
import com.rijksmuseum.masterpieces.services.collection.CollectionInteractor
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

/**
 * Interface for [MasterpieceDetailsFragment] ViewModel
 */
interface MasterpieceDetailsViewModel {

    val screenState: LiveData<DetailsScreenState>

    fun reloadData()
}

class MasterpieceDetailsViewModelImpl @Inject constructor(
    private val locale: Locale,
    private val route: MasterpieceDetailsFragmentRoute,
    private val schedulersProvider: SchedulersProvider,
    private val collectionInteractor: CollectionInteractor
) : MasterpieceDetailsViewModel, ViewModel() {

    override val screenState = MutableLiveData(
        DetailsScreenState(route.artObject, RequestUi())
    )

    private val disposables = CompositeDisposable()

    init {
        Log.v("MasterpieceDetailsVM", "${hashCode()}")
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
                .doOnSubscribe {
                    screenState.value = screenState.value?.copy(
                        details = RequestUi(load = Loading.MAIN)
                    )
                }
                .subscribe(
                    { data ->
                        screenState.value = screenState.value?.copy(
                            details = RequestUi(data)
                        )
                    },
                    { error ->
                        screenState.value = screenState.value?.copy(
                            details = RequestUi(error = error)
                        )
                    }
                )
        )
    }
}