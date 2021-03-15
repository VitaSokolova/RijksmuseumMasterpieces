package com.rijksmuseum.masterpieces.features.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rijksmuseum.masterpieces.R
import com.rijksmuseum.masterpieces.databinding.FragmentMasterpiecesListBinding
import com.rijksmuseum.masterpieces.domain.ArtObject
import com.rijksmuseum.masterpieces.features.common.models.pagination.PaginationBundle
import com.rijksmuseum.masterpieces.features.details.MasterpieceDetailsFragment
import com.rijksmuseum.masterpieces.features.details.MasterpieceDetailsFragmentRoute
import com.rijksmuseum.masterpieces.features.list.controllers.*
import com.rijksmuseum.masterpieces.features.list.di.MasterpiecesListScreenConfigurator
import ru.surfstudio.android.easyadapter.ItemList
import ru.surfstudio.android.easyadapter.pagination.EasyPaginationAdapter
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import javax.inject.Inject

/**
 * Fragment, which shows the list of masterpieces
 */
class MasterpiecesListFragment : Fragment() {

    @Inject
    lateinit var viewModel: MasterpiecesListViewModel

    private val viewBinding by viewBinding(FragmentMasterpiecesListBinding::bind)

    private val easyAdapter = EasyPaginationAdapter(
        PaginationFooterItemController()
    ) { viewModel.loadNextPage() }

    private val headerController = HeaderItemController()
    private val stubController = StubArtObjectController()
    private val errorController = ErrorPlaceholderController { viewModel.loadFirstPage() }
    private val artObjectController = ArtObjectController { openDetailsScreen(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_masterpieces_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MasterpiecesListScreenConfigurator().inject(this)
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        with(viewBinding) {
            recycler.layoutManager = LinearLayoutManager(context)
            recycler.adapter = easyAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.artObjects.observe(
            viewLifecycleOwner,
            Observer { requestUi ->
                when {
                    requestUi.data != null -> renderArtObjects(requestUi?.data)
                    requestUi.isLoading -> renderLoading()
                    requestUi.hasError -> renderErrorPlaceholder()
                }
            }
        )
    }

    private fun renderArtObjects(paginationBundle: PaginationBundle<ArtObject>) {
        val items = paginationBundle.list ?: emptyList<ArtObject>()
        easyAdapter.setItems(
            ItemList.create().apply {
                items.forEachIndexed { index, artObject ->
                    if (index == 0) {
                        add(artObject.principalOrFirstMaker, headerController)
                    }

                    val nextTitle = items.getOrNull(index + 1)?.principalOrFirstMaker
                    val hasNext = nextTitle != null
                    val nextTitleIsDifferent = artObject.principalOrFirstMaker != nextTitle

                    if (hasNext && nextTitleIsDifferent) {
                        add(artObject, artObjectController)
                        add(nextTitle, headerController)
                    } else {
                        add(artObject, artObjectController)
                    }
                }
            },
            paginationBundle.state
        )
    }

    private fun renderLoading() {
        easyAdapter.setItems(
            ItemList.create().apply {
                repeat(STUBS_COUNT) { add(true, stubController) }
            },
            PaginationState.COMPLETE
        )
    }

    private fun renderErrorPlaceholder() {
        easyAdapter.setItems(
            ItemList.create().add(errorController),
            PaginationState.COMPLETE
        )
    }

    private fun openDetailsScreen(artObject: ArtObject) {
        activity?.supportFragmentManager?.commit {
            replace<MasterpieceDetailsFragment>(
                R.id.fragment_container,
                MasterpieceDetailsFragment.NAME,
                MasterpieceDetailsFragmentRoute(artObject).getBundle()

            )
            addToBackStack(MasterpieceDetailsFragment.NAME)
        }
    }

    companion object {
        const val NAME = "MasterpiecesListFragment"
        private const val STUBS_COUNT = 3
    }
}