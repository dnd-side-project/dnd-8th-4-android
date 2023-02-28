package com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseSticker
import com.dnd_8th_4_android.wery.databinding.ItemStickerBinding

class StickerAdapter(private val onItemClick: (ResponseSticker.Data.AcquisitionStickerInfo) -> Unit) :
    ListAdapter<ResponseSticker.Data.AcquisitionStickerInfo, StickerAdapter.StickerViewHolder>(
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
    }

    class StickerViewHolder(
        val binding: ItemStickerBinding,
        val onItemClick: (ResponseSticker.Data.AcquisitionStickerInfo) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        private var stickerData: ResponseSticker.Data.AcquisitionStickerInfo? = null

        init {
            binding.root.setOnClickListener {
                stickerData?.let {
                    if (it.isStickerLocked) onItemClick(it)
                }
            }
        }

        fun onBind(data: ResponseSticker.Data.AcquisitionStickerInfo) {
            stickerData = data
            binding.data = data
        }
    }

    companion object {
        private val stickerDiffUtil = object : DiffUtil.ItemCallback<ResponseSticker.Data.AcquisitionStickerInfo>() {
            override fun areItemsTheSame(
                oldItem: ResponseSticker.Data.AcquisitionStickerInfo,
                newItem: ResponseSticker.Data.AcquisitionStickerInfo,
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ResponseSticker.Data.AcquisitionStickerInfo,
                newItem: ResponseSticker.Data.AcquisitionStickerInfo,
            ): Boolean = oldItem == newItem
        }
    }
}