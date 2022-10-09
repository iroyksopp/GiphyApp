package com.example.giphyapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphyapp.domain.GifUiEntity
import com.example.giphyapp.databinding.ItemGifBinding

class PagingAdapter(
    private val clickListener: (GifUiEntity) -> Unit,
    private val deleteClickListener:(GifUiEntity) -> Unit
) : PagingDataAdapter<GifUiEntity, PagingAdapter.Holder>(GifItemCallback()) {

    class Holder(val binding: ItemGifBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val gif = getItem(position) ?: return
        with(holder.binding) {
            Glide.with(gifImageView.context).load(gif.previewUrl).into(gifImageView)
            gifTitle.text = gif.title
            deleteGif.setOnClickListener { deleteClickListener(gif) }
            gifImageView.setOnClickListener { clickListener(gif) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGifBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }
}