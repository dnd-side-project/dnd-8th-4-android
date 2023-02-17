package com.dnd_8th_4_android.wery.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import com.dnd_8th_4_android.wery.databinding.ItemPostDetailEmotionBinding

class PostDetailEmotionRecyclerViewAdapter :
    ListAdapter<ResponsePostDetailEmotionData.Data, PostDetailEmotionRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemPostDetailEmotionBinding

    class ViewHolder(private val binding: ItemPostDetailEmotionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pair<Int, Int>) {
            binding.ivFriendImage.clipToOutline = true
            binding.ivEmotion.clipToOutline = true
            Glide.with(binding.ivFriendImage.context).load(item.first)
                .into(binding.ivFriendImage)

            Glide.with(binding.ivEmotion.context).load(item.second)
                .into(binding.ivEmotion)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemPostDetailEmotionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position].imageEmotion)
    }

    companion object {
        private val diffUtil =
            object : DiffUtil.ItemCallback<ResponsePostDetailEmotionData.Data>() {
                override fun areItemsTheSame(
                    oldItem: ResponsePostDetailEmotionData.Data,
                    newItem: ResponsePostDetailEmotionData.Data,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ResponsePostDetailEmotionData.Data,
                    newItem: ResponsePostDetailEmotionData.Data,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}