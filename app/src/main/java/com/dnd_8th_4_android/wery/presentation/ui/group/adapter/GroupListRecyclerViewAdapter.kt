package com.dnd_8th_4_android.wery.presentation.ui.group.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupListData
import com.dnd_8th_4_android.wery.databinding.ItemGroupListBinding

class GroupListRecyclerViewAdapter :
    ListAdapter<ResponseGroupListData.Data, GroupListRecyclerViewAdapter.ViewHolder>(diffUtil) {
    private lateinit var binding: ItemGroupListBinding
    private lateinit var bookmarkClickListener: BookmarkClickListener

    inner class ViewHolder(private val binding: ItemGroupListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseGroupListData.Data) {
            binding.ivGroupImage.clipToOutline = true
            Glide.with(binding.ivGroupImage.context).load(item.image)
                .into(binding.ivGroupImage)
            binding.tvGroupName.text = item.name
            binding.tvGroupIntroduce.text = item.introduce
            binding.tvGroupNumber.text = item.number.toString()
            binding.ivGroupBookmark.isSelected = item.isSelected

            binding.ivGroupBookmark.setOnClickListener {
                bookmarkClickListener.onClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemGroupListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun setBookmarkClickListener(listener: (Int) -> Unit) {
        bookmarkClickListener = object : BookmarkClickListener {
            override fun onClicked(position: Int) {
                listener(position)
            }
        }
    }

    interface BookmarkClickListener {
        fun onClicked(position: Int)
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ResponseGroupListData.Data>() {
            override fun areItemsTheSame(
                oldItem: ResponseGroupListData.Data,
                newItem: ResponseGroupListData.Data,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResponseGroupListData.Data,
                newItem: ResponseGroupListData.Data,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}