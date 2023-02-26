package com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.databinding.ItemStickerDetailBinding

class StickerDetailAdapter :
    RecyclerView.Adapter<StickerDetailAdapter.StickerDetailViewHolder>() {
    val itemList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerDetailViewHolder {
        val binding = ItemStickerDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StickerDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StickerDetailViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class StickerDetailViewHolder(var binding: ItemStickerDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imgValue: String) {
            Glide.with(binding.ivStickerDetail.context).load(imgValue)
                .into(binding.ivStickerDetail)
        }
    }
}