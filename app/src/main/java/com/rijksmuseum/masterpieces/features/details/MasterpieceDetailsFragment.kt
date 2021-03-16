package com.rijksmuseum.masterpieces.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.rijksmuseum.masterpieces.R
import com.rijksmuseum.masterpieces.databinding.FragmentMasterpieceDetailsBinding
import com.rijksmuseum.masterpieces.domain.ArtObjectBasics
import com.rijksmuseum.masterpieces.domain.ArtObjectDetailed
import com.rijksmuseum.masterpieces.features.common.debounce
import com.rijksmuseum.masterpieces.features.common.models.loading.RequestUi
import com.rijksmuseum.masterpieces.features.details.di.MasterpieceDetailsScreenConfigurator
import javax.inject.Inject
import kotlin.math.roundToInt

/**
 * Fragment, which shows  masterpiece's detailed info
 */
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
        observeViewModel()
    }

    private fun initViews() {
        viewBinding.backIb.background = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.background_arrow_ic
        ).apply {
            this?.alpha = (255 * 0.2).roundToInt()
        }
    }

    private fun initListeners() {
        viewBinding.backIb.setOnClickListener { closeScreen() }
        viewBinding.placeholder.retryBtn.setOnClickListener { viewModel.reloadData() }
    }

    private fun observeViewModel() {
        viewModel.screenState
            .debounce(
                STATE_CHANGES_DEBOUNCE,
                viewLifecycleOwner.lifecycleScope
            ) // to avoid loader and placeholder blinking
            .observe(
                viewLifecycleOwner,
                Observer { state ->
                    renderBasicInfo(state.basics)
                    renderDetailsRequest(state.details)
                }
            )

        viewModel.showErrorMsg.observe(
            viewLifecycleOwner,
            Observer { showSnack(resources.getString(R.string.masterpieces_list_error_placeholder_text)) })
    }

    private fun renderBasicInfo(data: ArtObjectBasics) {
        with(viewBinding) {
            titleTv.text = data.title

            principalMakerTv.text = resources.getString(
                R.string.masterpiece_details_author_text,
                data.principalOrFirstMaker
            )

            data.imageUri?.let {
                Glide
                    .with(requireContext())
                    .load(data.imageUri)
                    .placeholder(R.drawable.ic_painting_placeholder)
                    .into(paintingIv)
            }
        }
    }

    private fun renderDetailsRequest(request: RequestUi<ArtObjectDetailed>) {
        with(viewBinding) {
            loader.isVisible = request.isLoading
            placeholder.placeholderContainer.isVisible = request.hasError
            datingTv.isVisible = request.hasData
            descriptionTV.isVisible = request.hasData
            request.data?.let { renderArtObjectDetails(it) }
        }
    }

    private fun renderArtObjectDetails(data: ArtObjectDetailed) {
        with(viewBinding) {
            datingTv.isVisible = data.dating.isNotEmpty()
            datingTv.text = resources.getString(
                R.string.masterpiece_details_dating_text,
                data.dating
            )
            descriptionTV.text = data.description
        }
    }

    private fun showSnack(text: String) {
        Snackbar.make(viewBinding.root, text, Snackbar.LENGTH_SHORT).show()
    }

    private fun closeScreen() {
        activity?.supportFragmentManager?.popBackStack()
    }

    companion object {
        const val NAME = "MasterpieceDetailsFragment"

        private const val STATE_CHANGES_DEBOUNCE = 250L
    }
}