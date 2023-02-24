package com.dnd_8th_4_android.wery.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import com.dnd_8th_4_android.wery.databinding.ItemPostDetailEmotionBinding
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType

class PostDetailEmotionRecyclerViewAdapter :
    ListAdapter<ResponsePostDetailEmotionData.Data, PostDetailEmotionRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemPostDetailEmotionBinding

    class ViewHolder(private val binding: ItemPostDetailEmotionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponsePostDetailEmotionData.Data) {
            binding.ivFriendImage.clipToOutline = true
            binding.ivEmotion.clipToOutline = true

            if(item.userImage != null) {
                Glide.with(binding.ivFriendImage.context).load(item.userImage)
                    .into(binding.ivFriendImage)
            }

            if (item.emotionStatus != -1) {
                val emotionDrawable = when (item.emotionStatus) {
                    PopupWindowType.Type1.emotionPosition -> {
                        PopupWindowType.Type1.drawable
                    }
                    PopupWindowType.Type2.emotionPosition -> {
                        PopupWindowType.Type2.drawable
                    }
                    PopupWindowType.Type3.emotionPosition -> {
                        PopupWindowType.Type3.drawable
                    }
                    PopupWindowType.Type4.emotionPosition -> {
                        PopupWindowType.Type4.drawable
                    }
                    PopupWindowType.Type5.emotionPosition -> {
                        PopupWindowType.Type5.drawable
                    }
                    else -> {
                        PopupWindowType.Type6.drawable
                    }
                }

                Glide.with(binding.ivEmotion.context).load(emotionDrawable)
                    .into(binding.ivEmotion)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemPostDetailEmotionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
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