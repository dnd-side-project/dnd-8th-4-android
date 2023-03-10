package com.dnd_8th_4_android.wery.presentation.ui.group.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupInformationData
import com.dnd_8th_4_android.wery.databinding.ItemGroupInformationBinding

class GroupInformationRecyclerViewAdapter :
    ListAdapter<ResponseGroupInformationData.Data.GroupMemberInfoList, GroupInformationRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemGroupInformationBinding

    class ViewHolder(private val binding: ItemGroupInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseGroupInformationData.Data.GroupMemberInfoList) {
            binding.ivFriendImage.clipToOutline = true
            Glide.with(binding.ivFriendImage.context).load(item.userProfileImageUrl)
                .into(binding.ivFriendImage)

            binding.tvFriendName.text = item.userName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemGroupInformationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffUtil = object :
            DiffUtil.ItemCallback<ResponseGroupInformationData.Data.GroupMemberInfoList>() {
            override fun areItemsTheSame(
                oldItem: ResponseGroupInformationData.Data.GroupMemberInfoList,
                newItem: ResponseGroupInformationData.Data.GroupMemberInfoList,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResponseGroupInformationData.Data.GroupMemberInfoList,
                newItem: ResponseGroupInformationData.Data.GroupMemberInfoList,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}