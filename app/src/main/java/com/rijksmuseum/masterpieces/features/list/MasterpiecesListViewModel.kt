package com.rijksmuseum.masterpieces.features.list

import androidx.lifecycle.ViewModel
import com.rijksmuseum.masterpieces.domain.ArtObject
import com.rijksmuseum.masterpieces.services.collection.CollectionInteractor
import io.reactivex.rxjava3.core.Single
import ru.surfstudio.android.datalistpagecount.domain.datalist.DataList
import java.util.*
import javax.inject.Inject

/**
 * Interface for [MasterpiecesListFragment] ViewModel
 */
interface MasterpiecesListViewModel {
    fun loadData(): Single<DataList<ArtObject>>
}

class MasterpiecesListViewModelImpl @Inject constructor(
    private val collectionInteractor: CollectionInteractor
) : ViewModel(), MasterpiecesListViewModel {

    override fun loadData(): Single<DataList<ArtObject>> {
        //todo: change locale
        return collectionInteractor.getTopMasterpieces(Locale.ENGLISH, 0, 10)
    }
}