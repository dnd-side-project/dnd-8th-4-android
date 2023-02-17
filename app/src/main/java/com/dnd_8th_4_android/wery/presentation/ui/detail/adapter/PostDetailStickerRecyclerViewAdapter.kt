package com.dnd_8th_4_android.wery.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailStickerData
import com.dnd_8th_4_android.wery.databinding.ItemPostDetailStickerBinding

class PostDetailStickerRecyclerViewAdapter(private val list: ResponsePostDetailStickerData.Data) : RecyclerView.Adapter<PostDetailStickerRecyclerViewAdapter.ViewHolder>() {
    private lateinit var binding : ItemPostDetailStickerBinding

    class ViewHolder(private val binding: ItemPostDetailStickerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            Glide.with(binding.ivSticker.context).load(item)
                .into(binding.ivSticker)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPostDetailStickerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.stickerList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.stickerList[position])
    }
}