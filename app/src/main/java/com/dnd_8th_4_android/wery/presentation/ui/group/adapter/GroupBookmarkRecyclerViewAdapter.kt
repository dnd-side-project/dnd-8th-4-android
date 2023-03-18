package com.dnd_8th_4_android.wery.presentation.ui.group.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseBookmarkData
import com.dnd_8th_4_android.wery.databinding.ItemMyGroupBinding

class GroupBookmarkRecyclerViewAdapter :
    ListAdapter<ResponseBookmarkData.Data, GroupBookmarkRecyclerViewAdapter.ViewHolder>(diffUtil) {
    private lateinit var binding: ItemMyGroupBinding

    inner class ViewHolder(private val binding: ItemMyGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseBookmarkData.Data) {
            binding.ivMyGroup.clipToOutline = true
            Glide.with(binding.ivMyGroup.context)
                .load(item.groupImageUrl)
                .centerCrop()
                .into(binding.ivMyGroup)


            binding.tvGroupName.text = item.groupName

            itemView.setOnClickListener {
                goToAccessGroup(item.groupId.toString(), item.groupImageUrl, item.memberCount)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemMyGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private fun goToAccessGroup(groupId: String, groupImage: String, memberCount: Int) {
        val bundle = Bundle()
        bundle.putString(
            GroupListRecyclerViewAdapter.GROUP_NAME,
            binding.tvGroupName.text.toString()
        )
        bundle.putInt(GroupListRecyclerViewAdapter.GROUP_NUMBER, memberCount)
        bundle.putString(GroupListRecyclerViewAdapter.GROUP_Id, groupId)
        bundle.putString(GroupListRecyclerViewAdapter.GROUP_IMAGE, groupImage)
        bundle.putBoolean(GroupListRecyclerViewAdapter.GROUP_BOOKMARK, true)

        binding.root.findNavController().navigate(
            R.id.action_groupFragment_to_accessGroupFragment, bundle
        )
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