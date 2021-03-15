package com.rijksmuseum.masterpieces.features.list.controllers

import android.view.ViewGroup
import com.rijksmuseum.masterpieces.R
import com.rijksmuseum.masterpieces.databinding.ListItemTitleHeaderBinding
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

/**
 * Controller with items group header
 */
class HeaderItemController : BindableItemController<String, HeaderItemController.Holder>() {

    override fun getItemId(data: String): Any = data

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    inner class Holder(parent: ViewGroup) :
        BindableViewHolder<String>(parent, R.layout.list_item_title_header) {

        private val binding = ListItemTitleHeaderBinding.bind(itemView)

        override fun bind(title: String) {
            binding.titleTv.text = title
        }
    }
}