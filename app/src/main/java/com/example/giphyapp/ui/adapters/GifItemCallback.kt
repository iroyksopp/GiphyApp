package com.example.giphyapp.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.giphyapp.domain.GifUiEntity

class GifItemCallback : DiffUtil.ItemCallback<GifUiEntity>() {
    override fun areContentsTheSame(oldItem: GifUiEntity, newItem: GifUiEntity): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: GifUiEntity, newItem: GifUiEntity): Boolean {
        return oldItem.id == newItem.id
    }
}