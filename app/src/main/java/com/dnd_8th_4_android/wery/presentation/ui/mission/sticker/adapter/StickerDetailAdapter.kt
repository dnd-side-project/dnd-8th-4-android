package com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseStickerDetail
import com.dnd_8th_4_android.wery.databinding.ItemStickerDetailBinding

class StickerDetailAdapter(private val itemList: MutableList<ResponseStickerDetail.Data.StickerInfoList>) :
    RecyclerView.Adapter<StickerDetailAdapter.StickerDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerDetailViewHolder {
        val binding =
            ItemStickerDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StickerDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StickerDetailViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    class StickerDetailViewHolder(var binding: ItemStickerDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imgValue: ResponseStickerDetail.Data.StickerInfoList) {
            Glide.with(binding.ivStickerDetail.context).load(imgValue.stickerImageUrl)
                .into(binding.ivStickerDetail)
        }
    }
}