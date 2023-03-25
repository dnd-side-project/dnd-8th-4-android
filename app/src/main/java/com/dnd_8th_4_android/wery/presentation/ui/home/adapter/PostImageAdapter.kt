package com.dnd_8th_4_android.wery.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.databinding.ItemPostImageBinding

class PostImageAdapter :
    ListAdapter<ResponsePostData.Data.Content.Images, PostImageAdapter.ViewHolder>(diffUtil) {
    private lateinit var binding: ItemPostImageBinding
    private lateinit var postDetailImageListener: PostDetailImageListener

    inner class ViewHolder(private val binding: ItemPostImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponsePostData.Data.Content.Images) {
            binding.ivFriendPostImage.clipToOutline = true
            Glide.with(binding.ivFriendPostImage.context).load(item.imageUrl)
                .into(binding.ivFriendPostImage)
            binding.tvPostImageCount.text = binding.root.resources.getString(
                R.string.home_post_image,
                adapterPosition.inc(),
                currentList.size
            )

            if (currentList.size == 1) {
                binding.tvPostImageCount.isVisible = false
            }

            binding.ivFriendPostImage.setOnClickListener {
                postDetailImageListener.onClicked()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPostImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun setPostDetailImageListener(listener: () -> Unit) {
        postDetailImageListener = object : PostDetailImageListener {
            override fun onClicked() {
                listener()
            }
        }
    }

    interface PostDetailImageListener {
        fun onClicked()
    }

    companion object {
        private val diffUtil =
            object : DiffUtil.ItemCallback<ResponsePostData.Data.Content.Images>() {
                override fun areItemsTheSame(
                    oldItem: ResponsePostData.Data.Content.Images,
                    newItem: ResponsePostData.Data.Content.Images,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ResponsePostData.Data.Content.Images,
                    newItem: ResponsePostData.Data.Content.Images,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}