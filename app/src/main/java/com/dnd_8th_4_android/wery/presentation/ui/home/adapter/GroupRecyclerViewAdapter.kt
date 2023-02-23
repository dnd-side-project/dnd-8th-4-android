package com.dnd_8th_4_android.wery.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.databinding.ItemMyGroupBinding

class GroupRecyclerViewAdapter(
    initItemImage: View,
    initItemText: TextView,
) :
    ListAdapter<ResponseGroupData.Data.GroupInfo, GroupRecyclerViewAdapter.ViewHolder>(diffUtil) {
    private lateinit var binding: ItemMyGroupBinding

    var selectedItemImage = initItemImage
    var selectedItemText = initItemText

    private lateinit var groupPostCallListener: GroupPostCallListener

    inner class ViewHolder(private val binding: ItemMyGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseGroupData.Data.GroupInfo) {
            binding.ivMyGroup.clipToOutline = true
            Glide.with(binding.ivMyGroup.context).load(item.image)
                .into(binding.ivMyGroup)

            binding.tvGroupName.text = item.name

            binding.layoutMyGroup.setOnClickListener {
                if (selectedItemImage != binding.layoutMyGroupImage) {
                    selectedItemImage.isSelected = false
                    selectedItemText.setTextAppearance(R.style.TextView_Caption_12_R)

                    binding.tvGroupName.setTextAppearance(R.style.TextView_Title_12_Sb)
                    binding.layoutMyGroupImage.isSelected = !binding.layoutMyGroupImage.isSelected

                    selectedItemImage = binding.layoutMyGroupImage
                    selectedItemText = binding.tvGroupName

                    groupPostCallListener.onClicked(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            ItemMyGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun setGroupPostCallListener(listener: (Int) -> Unit) {
        groupPostCallListener = object : GroupPostCallListener {
            override fun onClicked(groupId: Int) {
                listener(groupId)
            }
        }
    }

    interface GroupPostCallListener {
        fun onClicked(groupId: Int)
    }

    companion object {
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