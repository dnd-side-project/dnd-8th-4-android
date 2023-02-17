package com.dnd_8th_4_android.wery.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.databinding.ItemAllMyGroupBinding
import com.dnd_8th_4_android.wery.databinding.ItemMyGroupBinding

class GroupRecyclerViewAdapter :
    ListAdapter<ResponseGroupData.Data, RecyclerView.ViewHolder>(diffUtil) {
    private lateinit var selectedItem: View

    inner class AllGroupViewHolder(private val binding: ItemAllMyGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.layoutAllMyGroup.setOnClickListener {
                isSelected(itemView)
            }
        }
    }

    inner class GroupViewHolder(private val binding: ItemMyGroupBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseGroupData.Data) {
            binding.ivMyGroup.clipToOutline = true
            binding.tvGroupName.text = item.name
            binding.layoutMyGroup.setOnClickListener {
                isSelected(itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_ALL_GROUP -> {
                val binding = ItemAllMyGroupBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                binding.layoutAllGroup.isSelected = true
                selectedItem = binding.layoutAllGroup
                AllGroupViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemMyGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GroupViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        val originSize = currentList.size
        return if (originSize == 0) 0 else originSize.inc()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AllGroupViewHolder -> {
                holder.bind()
            }
            is GroupViewHolder -> {
                holder.bind(currentList[position.dec()])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) ITEM_ALL_GROUP else ITEM_GROUP
    }

    private fun isSelected(itemView: View) {
        if (selectedItem != itemView) {
            selectedItem.isSelected = false
            itemView.isSelected = !itemView.isSelected
            selectedItem = itemView
        }
    }

    companion object {
        private const val ITEM_ALL_GROUP = 0
        private const val ITEM_GROUP = 1

        private val diffUtil = object : DiffUtil.ItemCallback<ResponseGroupData.Data>() {
            override fun areItemsTheSame(
                oldItem: ResponseGroupData.Data,
                newItem: ResponseGroupData.Data,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResponseGroupData.Data,
                newItem: ResponseGroupData.Data,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}