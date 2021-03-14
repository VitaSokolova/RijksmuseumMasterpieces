package com.rijksmuseum.masterpieces.features.list.controllers

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.rijksmuseum.masterpieces.R
import com.rijksmuseum.masterpieces.databinding.ListItemArtObjectBinding
import com.rijksmuseum.masterpieces.domain.ArtObject
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

/**
 * Controller which shows received information about art object
 */
class ArtObjectController(val onClickListener: (ArtObject) -> Unit) :
    BindableItemController<ArtObject, ArtObjectController.Holder>() {

    override fun getItemId(data: ArtObject): Any = data.objectNumber

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(parent: ViewGroup) : BindableViewHolder<ArtObject>(
        parent,
        R.layout.list_item_art_object
    ) {

        private lateinit var artObject: ArtObject

        private val binding = ListItemArtObjectBinding.bind(itemView)

        init {
            itemView.setOnClickListener { onClickListener(artObject) }
        }

        override fun bind(data: ArtObject) {
            this.artObject = data
            binding.titleTv.text = data.title

            Glide
                .with(itemView.context)
                .load(data.imageUri)
                .centerCrop()
                .placeholder(R.drawable.ic_painting_placeholder)
                .into(binding.imageIv)
        }
    }
}