package com.rijksmuseum.masterpieces.features.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rijksmuseum.masterpieces.R
import com.rijksmuseum.masterpieces.features.list.di.MasterpiecesListScreenConfigurator
import javax.inject.Inject


/**
 * Fragment, which shows the list of masterpieces
 */
class MasterpiecesListFragment : Fragment() {

    @Inject
    lateinit var viewModel: MasterpiecesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MasterpiecesListScreenConfigurator()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_masterpieces_list, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MasterpiecesListFragment.
         */
        @JvmStatic
        fun newInstance() = MasterpiecesListFragment().apply {
                arguments = Bundle()
            }

        const val NAME = "MasterpiecesListFragment"
    }
}