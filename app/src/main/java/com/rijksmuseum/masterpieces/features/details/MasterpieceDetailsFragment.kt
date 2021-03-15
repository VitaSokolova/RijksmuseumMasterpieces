package com.rijksmuseum.masterpieces.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.rijksmuseum.masterpieces.R
import com.rijksmuseum.masterpieces.databinding.FragmentMasterpieceDetailsBinding
import com.rijksmuseum.masterpieces.domain.ArtObjectDetailed
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
        viewBinding.backIb.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.background_arrow_ic).apply {
                this?.alpha = (255 * 0.2).roundToInt()
            }
    }

    private fun initListeners() {
        viewBinding.backIb.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun observeViewModel() {
        viewModel.screenState.observe(
            viewLifecycleOwner,
            Observer { data ->
                with(viewBinding) {
                    Glide
                        .with(requireContext())
                        .load(data.basics.imageUri)
                        .placeholder(R.drawable.ic_painting_placeholder)
                        .into(paintingIv)

                    when {
                        data.details.data != null -> {
                            renderArtObjectDetails(data.details.data)
                        }
                        data.details.isLoading -> {
                            //todo: render load state
                        }
                        data.details.hasError -> {
                            //todo: render error state
                        }
                    }
                }
            }
        )
    }

    private fun renderArtObjectDetails(data: ArtObjectDetailed) {
        with(viewBinding) {
            titleTv.text = data.title

            principalMakerTv.text = resources.getString(
                R.string.masterpiece_details_author_text,
                data.principalOrFirstMaker
            )

            datingTv.isVisible = data.dating.isNotEmpty()
            datingTv.text =
                resources.getString(R.string.masterpiece_details_dating_text, data.dating)

            descriptionTV.text = data.description
        }
    }

    companion object {
        const val NAME = "MasterpieceDetailsFragment"
    }
}