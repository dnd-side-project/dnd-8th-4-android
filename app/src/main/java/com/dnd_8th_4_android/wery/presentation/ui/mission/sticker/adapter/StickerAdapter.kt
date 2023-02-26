package com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseSticker
import com.dnd_8th_4_android.wery.databinding.ItemStickerBinding

class StickerAdapter(private val onItemClick: (ResponseSticker) -> Unit) :
    ListAdapter<ResponseSticker, StickerAdapter.StickerViewHolder>(
        stickerDiffUtil
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerViewHolder {
        val binding =
            ItemStickerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StickerViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: StickerViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.binding.ivSticker.clipToOutline = true
        if (position == (itemCount - 1)) holder.binding.viewLine.visibility = View.GONE
    }

    class StickerViewHolder(
        val binding: ItemStickerBinding,
        val onItemClick: (ResponseSticker) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var stickerData: ResponseSticker? = null

        init {
            binding.root.setOnClickListener {
                stickerData?.let {
                    if (!it.isStickerLocked) onItemClick(it)
                }
            }
        }

        fun onBind(data: ResponseSticker) {
            stickerData = data
            binding.data = data
        }
    }

    companion object {
        private val stickerDiffUtil = object : DiffUtil.ItemCallback<ResponseSticker>() {
            override fun areItemsTheSame(
                oldItem: ResponseSticker,
                newItem: ResponseSticker
            ): Boolean =
                oldItem.stickerName == newItem.stickerName

            override fun areContentsTheSame(
                oldItem: ResponseSticker,
                newItem: ResponseSticker
            ): Boolean =
                oldItem == newItem
        }
    }
}