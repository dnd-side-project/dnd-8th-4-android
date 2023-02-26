package com.dnd_8th_4_android.wery.presentation.ui.group.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseBookmarkData
import com.dnd_8th_4_android.wery.databinding.ItemMyGroupBinding

class GroupBookmarkRecyclerViewAdapter :
    ListAdapter<ResponseBookmarkData.Data, GroupBookmarkRecyclerViewAdapter.ViewHolder>(diffUtil) {
    private lateinit var binding: ItemMyGroupBinding

    class ViewHolder(private val binding: ItemMyGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseBookmarkData.Data) {
            binding.ivMyGroup.clipToOutline = true
//            Glide.with(binding.ivMyGroup.context).load(item.)
//                .into(binding.ivMyGroup)

            binding.tvGroupName.text = item.groupName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMyGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ResponseBookmarkData.Data>() {
            override fun areItemsTheSame(
                oldItem: ResponseBookmarkData.Data,
                newItem: ResponseBookmarkData.Data,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResponseBookmarkData.Data,
                newItem: ResponseBookmarkData.Data,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}