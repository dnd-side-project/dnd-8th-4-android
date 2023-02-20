package com.dnd_8th_4_android.wery.presentation.ui.group.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseAccessGroupData
import com.dnd_8th_4_android.wery.databinding.ItemYesMissionBinding


class AccessGroupMissionRecyclerViewAdapter :
    ListAdapter<ResponseAccessGroupData.Data, AccessGroupMissionRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemYesMissionBinding

    class ViewHolder(private val binding: ItemYesMissionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseAccessGroupData.Data) {
            binding.tvRemainDay.text = item.day
            binding.tvMissionContent.text = item.content
            binding.tvStartDay.text = item.startDay
            binding.tvEndDay.text = item.endDay

            // TODO 버튼 Selected 처리
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemYesMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ResponseAccessGroupData.Data>() {
            override fun areItemsTheSame(
                oldItem: ResponseAccessGroupData.Data,
                newItem: ResponseAccessGroupData.Data,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResponseAccessGroupData.Data,
                newItem: ResponseAccessGroupData.Data,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}