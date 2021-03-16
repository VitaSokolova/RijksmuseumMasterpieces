package com.rijksmuseum.masterpieces.features.list.controllers

import android.view.ViewGroup
import androidx.core.view.isVisible
import com.rijksmuseum.masterpieces.R
import com.rijksmuseum.masterpieces.databinding.ListItemPaginationFooterBinding
import ru.surfstudio.android.easyadapter.pagination.EasyPaginationAdapter
import ru.surfstudio.android.easyadapter.pagination.PaginationState

/**
 * Controller with ProgressBar and "Retry" button, which
 * occurs at the bottom of list during pagination or after pagination error
 */
class PaginationFooterItemController :
    EasyPaginationAdapter.BasePaginationFooterController<PaginationFooterItemController.Holder>() {

    override fun createViewHolder(
        parent: ViewGroup,
        listener: EasyPaginationAdapter.OnShowMoreListener
    ): Holder {
        return Holder(parent, listener)
    }

    override fun getState(): PaginationState {
        return PaginationState.READY
    }

    inner class Holder(
        parent: ViewGroup,
        listener: EasyPaginationAdapter.OnShowMoreListener
    ) : EasyPaginationAdapter.BasePaginationFooterHolder(
        parent,
        R.layout.list_item_pagination_footer
    ) {

        private val binding = ListItemPaginationFooterBinding.bind(itemView)

        init {
            binding.retryBtn.setOnClickListener { listener.onShowMore() }
        }

        override fun bind(state: PaginationState) {
            val isLoading = state == PaginationState.READY
            val isError = state == PaginationState.ERROR

            binding.loader.isVisible = isLoading
            binding.retryBtn.isVisible = isError
        }
    }
}
