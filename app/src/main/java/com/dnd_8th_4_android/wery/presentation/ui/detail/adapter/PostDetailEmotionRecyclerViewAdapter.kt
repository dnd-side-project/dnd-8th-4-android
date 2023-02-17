package com.dnd_8th_4_android.wery.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import com.dnd_8th_4_android.wery.databinding.ItemPostDetailEmotionBinding
import com.dnd_8th_4_android.wery.databinding.ItemPostDetailEmotionPlusBinding

class PostDetailEmotionRecyclerViewAdapter :
    ListAdapter<ResponsePostDetailEmotionData.Data, RecyclerView.ViewHolder>(diffUtil) {

    class EmotionPlusViewHolder(private val binding: ItemPostDetailEmotionPlusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.layoutEmotionPlus.setOnClickListener {
                // TODO 감정 이모지 window popup 추가
            }
        }
    }

    class EmotionViewHolder(private val binding: ItemPostDetailEmotionBinding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_EMOTION_PLUS -> {
                val binding = ItemPostDetailEmotionPlusBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                EmotionPlusViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemPostDetailEmotionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                EmotionViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EmotionPlusViewHolder -> {
                holder.bind()
            }
            is EmotionViewHolder -> {
                holder.bind(currentList[position.dec()].imageEmotion)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) ITEM_EMOTION_PLUS else ITEM_EMOTION
    }

    override fun getItemCount(): Int {
        val originSize = currentList.size
        return if (originSize == 0) 0 else originSize.inc()
    }

    companion object {
        private const val ITEM_EMOTION_PLUS = 0
        private const val ITEM_EMOTION = 1

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