package com.dnd_8th_4_android.wery.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailImageData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.databinding.ItemPostDetailImageBinding

class PostDetailImageRecyclerViewAdapter :
    ListAdapter<ResponsePostDetailData.Data.Images, PostDetailImageRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemPostDetailImageBinding

    inner class ViewHolder(private val binding: ItemPostDetailImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponsePostDetailData.Data.Images) {
            binding.ivPostImage.clipToOutline = true

            Glide.with(binding.ivPostImage.context).load(item.imageUrl)
                .into(binding.ivPostImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemPostDetailImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffUtil =
            object : DiffUtil.ItemCallback<ResponsePostDetailData.Data.Images>() {
                override fun areItemsTheSame(
                    oldItem: ResponsePostDetailData.Data.Images,
                    newItem: ResponsePostDetailData.Data.Images,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ResponsePostDetailData.Data.Images,
                    newItem: ResponsePostDetailData.Data.Images,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}