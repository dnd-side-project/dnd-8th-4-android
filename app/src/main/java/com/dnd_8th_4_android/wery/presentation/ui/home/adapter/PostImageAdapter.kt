package com.dnd_8th_4_android.wery.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.databinding.ItemPostImageBinding

class PostImageAdapter(private val itemList: List<Int>) :
    RecyclerView.Adapter<PostImageAdapter.ViewHolder>() {
    private lateinit var binding: ItemPostImageBinding
    private lateinit var postDetailImageListener: PostDetailImageListener

    inner class ViewHolder(private val binding: ItemPostImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) {
            binding.ivFriendPostImage.clipToOutline = true
            Glide.with(binding.ivFriendPostImage.context).load(item)
                .into(binding.ivFriendPostImage)
            binding.tvPostImageCount.text = "${adapterPosition.inc()}"
            binding.tvPostImageAllCount.text = itemList.size.toString()

            if(itemList.size == 1) {
                binding.layoutPostImageCount.isVisible = false
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

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])
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
}