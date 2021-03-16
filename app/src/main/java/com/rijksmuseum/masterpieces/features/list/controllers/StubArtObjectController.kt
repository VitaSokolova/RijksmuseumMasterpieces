package com.rijksmuseum.masterpieces.features.list.controllers

import android.view.ViewGroup
import com.rijksmuseum.masterpieces.R
import com.rijksmuseum.masterpieces.databinding.ListItemArtObjectStubBinding
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

/**
 * Controller which shows shimmering stubs while data is loading
 */
class StubArtObjectController : BindableItemController<Boolean, StubArtObjectController.Holder>() {

    override fun getItemId(isLoading: Boolean): Any = "$TAG$isLoading"

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) : BindableViewHolder<Boolean>(
        parent,
        R.layout.list_item_art_object_stub
    ) {
        private val binding = ListItemArtObjectStubBinding.bind(itemView)

        override fun bind(isLoading: Boolean) {
            with(binding) {
                if (isLoading) {
                    titleShimmer.startShimmer()
                    iconShimmer.startShimmer()
                } else {
                    titleShimmer.stopShimmer()
                    iconShimmer.stopShimmer()
                }
            }
        }
    }

    private companion object {
        const val TAG = "ShimmerStubItemController"
    }
}