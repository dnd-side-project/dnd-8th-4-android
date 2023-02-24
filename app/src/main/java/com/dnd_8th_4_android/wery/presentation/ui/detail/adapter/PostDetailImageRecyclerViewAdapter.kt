package com.dnd_8th_4_android.wery.presentation.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailImageData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.databinding.ItemPostDetailImageBinding

class PostDetailImageRecyclerViewAdapter(private val list: List<ResponsePostData.Data.Content.Images>) :
    RecyclerView.Adapter<PostDetailImageRecyclerViewAdapter.ViewHolder>() {
    private lateinit var binding: ItemPostDetailImageBinding

    inner class ViewHolder(private val binding: ItemPostDetailImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponsePostData.Data.Content.Images) {
            binding.ivFriendPostImage.clipToOutline = true

            Glide.with(binding.ivFriendPostImage.context).load(item.imageUrl)
                .into(binding.ivFriendPostImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemPostDetailImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}