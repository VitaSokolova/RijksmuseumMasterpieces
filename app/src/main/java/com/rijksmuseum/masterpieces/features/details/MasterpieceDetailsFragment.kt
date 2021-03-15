package com.rijksmuseum.masterpieces.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rijksmuseum.masterpieces.R
import com.rijksmuseum.masterpieces.databinding.FragmentMasterpieceDetailsBinding
import com.rijksmuseum.masterpieces.features.details.di.MasterpieceDetailsScreenConfigurator
import com.rijksmuseum.masterpieces.utils.setDefaultIconTint
import javax.inject.Inject

class MasterpieceDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModel: MasterpieceDetailsViewModel

    private val viewBinding by viewBinding(FragmentMasterpieceDetailsBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_masterpiece_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MasterpieceDetailsScreenConfigurator().inject(this)
        initViews()
        initListeners()
    }

    private fun initViews() {
        viewBinding.toolbar.setDefaultIconTint()
    }

    private fun initListeners() {
        viewBinding.toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    companion object {
        const val NAME = "MasterpieceDetailsFragment"
    }
}