package com.dnd_8th_4_android.wery.presentation.ui.group.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseUserSearchData
import com.dnd_8th_4_android.wery.databinding.ItemGroupInformationBinding

class UserSearchRecyclerViewAdapter :
    ListAdapter<ResponseUserSearchData.Data.UserSearchInfoList, UserSearchRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemGroupInformationBinding

    class ViewHolder(private val binding: ItemGroupInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseUserSearchData.Data.UserSearchInfoList) {
            binding.ivFriendImage.clipToOutline = true
            Glide.with(binding.ivFriendImage.context).load(item.profileImageUrl)
                .into(binding.ivFriendImage)

            binding.tvFriendName.text = item.userNickName
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
        private val diffUtil =
            object : DiffUtil.ItemCallback<ResponseUserSearchData.Data.UserSearchInfoList>() {
                override fun areItemsTheSame(
                    oldItem: ResponseUserSearchData.Data.UserSearchInfoList,
                    newItem: ResponseUserSearchData.Data.UserSearchInfoList,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ResponseUserSearchData.Data.UserSearchInfoList,
                    newItem: ResponseUserSearchData.Data.UserSearchInfoList,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}