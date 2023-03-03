package com.dnd_8th_4_android.wery.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentImageData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentNoImageData
import com.dnd_8th_4_android.wery.databinding.ItemPostDetailCommentBinding
import com.dnd_8th_4_android.wery.databinding.ItemPostDetailCommentImageBinding
import java.time.LocalDate

class PostDetailCommentRecyclerViewAdapter :
    ListAdapter<ResponsePostDetailCommentData.Data.Content, ViewHolder>(diffUtil) {

    class CommentImageViewHolder(private val binding: ItemPostDetailCommentImageBinding) :
        ViewHolder(binding.root) {
        fun bind(item: ResponsePostDetailCommentImageData.Data) {
            if (item.userId == AuthLocalDataSource(binding.root.context).userId) {
                binding.layoutCommentImage.background = ContextCompat.getDrawable(binding.root.context, R.color.gray50)
            } else {
                binding.layoutCommentImage.background = ContextCompat.getDrawable(binding.root.context, R.color.white)
            }

            binding.ivFriendImage.clipToOutline = true
            Glide.with(binding.ivFriendImage.context).load(item.friendImage)
                .into(binding.ivFriendImage)

            binding.tvFriendName.text = item.name

            Glide.with(binding.ivSticker.context).load(item.sticker)
                .into(binding.ivSticker)

            if (LocalDate.now().toString() == item.time.substring(IntRange(0, 9))) {
                binding.tvTime.text = item.time.substring(IntRange(11, 15)).replace("-", ".")
            } else {
                binding.tvTime.text = item.time.substring(IntRange(2, 9)).replace("-", ".")
            }
        }
    }

    class CommentViewHolder(private val binding: ItemPostDetailCommentBinding) :
        ViewHolder(binding.root) {
        fun bind(item: ResponsePostDetailCommentNoImageData.Data) {
            if (item.userId == AuthLocalDataSource(binding.root.context).userId) {
                binding.layoutComment.background = ContextCompat.getDrawable(binding.root.context, R.color.gray50)
            } else {
                binding.layoutComment.background = ContextCompat.getDrawable(binding.root.context, R.color.white)
            }

            binding.ivFriendImage.clipToOutline = true
            Glide.with(binding.ivFriendImage.context).load(item.friendImage)
                .into(binding.ivFriendImage)

            binding.tvFriendName.text = item.name
            binding.tvFriendComment.text = item.comment
            if (LocalDate.now().toString() == item.time.substring(IntRange(0, 9))) {
                binding.tvTime.text = item.time.substring(IntRange(11, 15)).replace("-", ".")
            } else {
                binding.tvTime.text = item.time.substring(IntRange(2, 9)).replace("-", ".")
            }
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
                        commentImageData.userId,
                        commentImageData.profileImageUrl,
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
                        commentNoImageData.userId,
                        commentNoImageData.profileImageUrl,
                        commentNoImageData.name,
                        commentNoImageData.comment,
                        commentNoImageData.time
                    )
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].sticker != null) {
            ITEM_COMMENT_IMAGE
        } else {
            ITEM_COMMENT
        }
    }

    companion object {
        private const val ITEM_COMMENT_IMAGE = 0
        private const val ITEM_COMMENT = 1

        private val diffUtil =
            object : DiffUtil.ItemCallback<ResponsePostDetailCommentData.Data.Content>() {
                override fun areItemsTheSame(
                    oldItem: ResponsePostDetailCommentData.Data.Content,
                    newItem: ResponsePostDetailCommentData.Data.Content,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ResponsePostDetailCommentData.Data.Content,
                    newItem: ResponsePostDetailCommentData.Data.Content,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}