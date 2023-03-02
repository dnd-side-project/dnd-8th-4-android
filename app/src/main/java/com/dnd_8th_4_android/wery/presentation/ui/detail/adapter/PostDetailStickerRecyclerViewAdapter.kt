package com.dnd_8th_4_android.wery.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailStickerData
import com.dnd_8th_4_android.wery.databinding.ItemPostDetailStickerBinding
import com.pedromassango.doubleclick.DoubleClick
import com.pedromassango.doubleclick.DoubleClickListener

class PostDetailStickerRecyclerViewAdapter(private val list: MutableList<ResponsePostDetailStickerData.Data.StickerInfoList>) :
    RecyclerView.Adapter<PostDetailStickerRecyclerViewAdapter.ViewHolder>() {
    private lateinit var binding: ItemPostDetailStickerBinding
    private lateinit var stickerClickListener: StickerClickListener

    inner class ViewHolder(private val binding: ItemPostDetailStickerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponsePostDetailStickerData.Data.StickerInfoList) {
            Glide.with(binding.ivSticker.context).load(item.stickerImageUrl)
                .into(binding.ivSticker)

            binding.ivSticker.setOnClickListener(DoubleClick(object : DoubleClickListener {
                override fun onSingleClick(view: View?) {}

                override fun onDoubleClick(view: View?) {
                    stickerClickListener.onClicked(item.stickerId.toLong())
                }
            }))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemPostDetailStickerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setStickerClickListener(listener: (Long) -> Unit) {
        stickerClickListener = object : StickerClickListener {
            override fun onClicked(sticker: Long) {
                listener(sticker)
            }
        }
    }

    interface StickerClickListener {
        fun onClicked(sticker: Long)
    }
}