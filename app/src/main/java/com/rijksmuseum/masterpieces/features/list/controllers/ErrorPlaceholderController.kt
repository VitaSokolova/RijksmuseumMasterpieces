package com.rijksmuseum.masterpieces.features.list.controllers

import android.view.ViewGroup
import com.rijksmuseum.masterpieces.R
import com.rijksmuseum.masterpieces.databinding.ListItemArtObjectErrorPlaceholderBinding
import ru.surfstudio.android.easyadapter.controller.NoDataItemController
import ru.surfstudio.android.easyadapter.holder.BaseViewHolder

/**
 * Controller which shows placeholder with "retry" button in case of error on screen
 */
class ErrorPlaceholderController(
    val onRetryClickListener: () -> Unit
) : NoDataItemController<ErrorPlaceholderController.Holder>() {

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) : BaseViewHolder(
        parent,
        R.layout.list_item_art_object_error_placeholder
    ) {
        private val binding = ListItemArtObjectErrorPlaceholderBinding.bind(itemView)

        init {
            binding.retryBtn.setOnClickListener { onRetryClickListener() }
        }
    }
}