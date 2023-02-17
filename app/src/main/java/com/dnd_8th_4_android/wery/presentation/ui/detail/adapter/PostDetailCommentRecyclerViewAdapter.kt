package com.dnd_8th_4_android.wery.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentImageData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentNoImageData
import com.dnd_8th_4_android.wery.databinding.ItemPostDetailCommentBinding
import com.dnd_8th_4_android.wery.databinding.ItemPostDetailCommentImageBinding

class PostDetailCommentRecyclerViewAdapter :
    ListAdapter<ResponsePostDetailCommentData.Data, ViewHolder>(diffUtil) {

    class CommentImageViewHolder(private val binding: ItemPostDetailCommentImageBinding) :
        ViewHolder(binding.root) {
        fun bind(item: ResponsePostDetailCommentImageData.Data) {
            Glide.with(binding.ivFriendImage.context).load(item.friendImage)
                .into(binding.ivFriendImage)

            binding.tvFriendName.text = item.name

            Glide.with(binding.ivSticker.context).load(item.sticker)
                .into(binding.ivSticker)

            binding.tvTime.text = item.time
        }
    }

    class CommentViewHolder(private val binding: ItemPostDetailCommentBinding) :
        ViewHolder(binding.root) {
        fun bind(item: ResponsePostDetailCommentNoImageData.Data) {
            Glide.with(binding.ivFriendImage.context).load(item.friendImage)
                .into(binding.ivFriendImage)
            binding.tvFriendName.text = item.name
            binding.tvFriendComment.text = item.comment
            binding.tvTime.text = item.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_COMMENT_IMAGE -> {
                val binding = ItemPostDetailCommentImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CommentImageViewHolder(binding)
            }
            else -> {
                val binding = ItemPostDetailCommentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CommentViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is CommentImageViewHolder -> {
                val commentImageData = currentList[position]
                holder.bind(
                    ResponsePostDetailCommentImageData.Data(
                        commentImageData.friendImage,
                        commentImageData.name,
                        commentImageData.sticker,
                        commentImageData.time
                    )
                )
            }
            is CommentViewHolder -> {
                val commentNoImageData = currentList[position]
                holder.bind(
                    ResponsePostDetailCommentNoImageData.Data(
                        commentNoImageData.friendImage,
                        commentNoImageData.name,
                        commentNoImageData.comment,
                        commentNoImageData.time
                    )
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].sticker != 0) {
            ITEM_COMMENT_IMAGE
        } else {
            ITEM_COMMENT
        }
    }

    companion object {
        private const val ITEM_COMMENT_IMAGE = 0
        private const val ITEM_COMMENT = 1

        private val diffUtil =
            object : DiffUtil.ItemCallback<ResponsePostDetailCommentData.Data>() {
                override fun areItemsTheSame(
                    oldItem: ResponsePostDetailCommentData.Data,
                    newItem: ResponsePostDetailCommentData.Data,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ResponsePostDetailCommentData.Data,
                    newItem: ResponsePostDetailCommentData.Data,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}