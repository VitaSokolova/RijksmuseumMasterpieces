package com.rijksmuseum.masterpieces.features.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rijksmuseum.masterpieces.R
import com.rijksmuseum.masterpieces.databinding.FragmentMasterpiecesListBinding
import com.rijksmuseum.masterpieces.features.list.controllers.ArtObjectController
import com.rijksmuseum.masterpieces.features.list.controllers.ErrorPlaceholderController
import com.rijksmuseum.masterpieces.features.list.controllers.StubArtObjectController
import com.rijksmuseum.masterpieces.features.list.di.MasterpiecesListScreenConfigurator
import com.rijksmuseum.masterpieces.utils.getCurrentLocale
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList
import javax.inject.Inject

/**
 * Fragment, which shows the list of masterpieces
 */
class MasterpiecesListFragment : Fragment() {

    @Inject
    lateinit var viewModel: MasterpiecesListViewModel

    private val viewBinding by viewBinding(FragmentMasterpiecesListBinding::bind)

    private val easyAdapter = EasyAdapter()

    private val stubController = StubArtObjectController()
    private val errorController = ErrorPlaceholderController {
        viewModel.loadFirstPage(context.getCurrentLocale())
    }
    private val artObjectController = ArtObjectController {
        //todo: navigate to detail screen
    }

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
        viewModel.loadFirstPage(context.getCurrentLocale())
    }

    private fun initViews() {
        with(viewBinding) {
            recycler.adapter = easyAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.artObjects.observe(viewLifecycleOwner, Observer { data ->
            easyAdapter.setItems(
                ItemList.create().apply {
                    when {
                        data.hasData -> addAll(data.data?.list ?: emptyList(), artObjectController)
                        data.isLoading -> repeat(STUBS_COUNT) { add(true, stubController) }
                        data.hasError -> add(errorController)
                    }
                }
            )
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MasterpiecesListFragment.
         */
        @JvmStatic
        fun newInstance() = MasterpiecesListFragment()

        const val NAME = "MasterpiecesListFragment"

        const val STUBS_COUNT = 2
    }
}