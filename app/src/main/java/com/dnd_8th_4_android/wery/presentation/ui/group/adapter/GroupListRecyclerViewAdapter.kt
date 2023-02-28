package com.dnd_8th_4_android.wery.presentation.ui.group.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.databinding.ItemGroupListBinding

class GroupListRecyclerViewAdapter :
    ListAdapter<ResponseGroupData.Data.GroupInfo, GroupListRecyclerViewAdapter.ViewHolder>(diffUtil) {
    private lateinit var binding: ItemGroupListBinding
    private lateinit var bookmarkClickListener: BookmarkClickListener

    inner class ViewHolder(private val binding: ItemGroupListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseGroupData.Data.GroupInfo) {
            binding.ivGroupImage.clipToOutline = true
            Glide.with(binding.ivGroupImage.context).load(item.image)
                .into(binding.ivGroupImage)
            binding.tvGroupName.text = item.name
            binding.tvGroupIntroduce.text = item.groupNote
            binding.tvGroupNumber.text = item.memberCount.toString()
            binding.ivGroupBookmark.isSelected = item.isStarGroup

            binding.ivGroupBookmark.setOnClickListener {
                bookmarkClickListener.onClicked(item.id)
            }

            binding.layer.setOnClickListener {
                goToAccessGroup(item.id.toString(), item.name, item.image, item.isStarGroup)
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

    private fun goToAccessGroup(groupId: String, groupName: String, groupImage: String, isStartGroup: Boolean) {
        val bundle = Bundle()
        bundle.putString(GROUP_NAME, groupName)
        bundle.putString(GROUP_NUMBER, binding.tvGroupNumber.text.toString())
        bundle.putString(GROUP_Id, groupId)
        bundle.putString(GROUP_IMAGE, groupImage)
        bundle.putBoolean(GROUP_BOOKMARK, isStartGroup)

        binding.root.findNavController().navigate(
            R.id.action_groupFragment_to_accessGroupFragment, bundle
        )
    }

    fun setBookmarkClickListener(listener: (Int) -> Unit) {
        bookmarkClickListener = object : BookmarkClickListener {
            override fun onClicked(groupId: Int) {
                listener(groupId)
            }
        }
    }

    interface BookmarkClickListener {
        fun onClicked(groupId: Int)
    }

    companion object {
        const val GROUP_NAME = "group_name"
        const val GROUP_NUMBER = "group_number"
        const val GROUP_Id = "group_id"
        const val GROUP_IMAGE = "group_image"
        const val GROUP_BOOKMARK = "group_bookmark"

        private val diffUtil = object : DiffUtil.ItemCallback<ResponseGroupData.Data.GroupInfo>() {
            override fun areItemsTheSame(
                oldItem: ResponseGroupData.Data.GroupInfo,
                newItem: ResponseGroupData.Data.GroupInfo,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResponseGroupData.Data.GroupInfo,
                newItem: ResponseGroupData.Data.GroupInfo,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}