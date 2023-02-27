package com.dnd_8th_4_android.wery.presentation.ui.group.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupMissionData
import com.dnd_8th_4_android.wery.databinding.ItemYesMissionBinding
import com.dnd_8th_4_android.wery.domain.model.MissionColor

class AccessGroupMissionRecyclerViewAdapter :
    ListAdapter<ResponseGroupMissionData.Data, AccessGroupMissionRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemYesMissionBinding

    inner class ViewHolder(private val binding: ItemYesMissionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseGroupMissionData.Data) {

            binding.ivFire.isVisible = item.missionDday in -1..3

            if (item.existPeriod) {
                binding.tvRemainDay.text = if (item.missionDday != -1) {
                    binding.root.resources.getString(R.string.access_group_day, item.missionDday)
                } else {
                    binding.root.resources.getString(R.string.access_group_d_day)
                }
            } else {
                binding.root.resources.getString(R.string.access_group_day_ing)
            }

            binding.tvMissionContent.text = item.missionTitle
            binding.tvStartDay.text = item.missionStartDate.substring(IntRange(2, 9))
            binding.tvEndDay.text = item.missionEndDate.substring(IntRange(2, 9))

            binding.layoutMission.backgroundTintList = when (item.missionColor) {
                MissionColor.BLUE.colorNumber -> {
                     ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, MissionColor.BLUE.color))
                }
                MissionColor.PINK.colorNumber -> {
                    ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, MissionColor.PINK.color))
                }
                else -> {
                    ColorStateList.valueOf(ContextCompat.getColor(binding.root.context, MissionColor.GREEN.color))
                }
            }

//            if (item.userAssignMissionInfoList[0].locationCheck) {
//                binding.btnCertify.icon =
//                    ContextCompat.getDrawable(binding.root.context, R.drawable.ic_check_write)
//                binding.btnCertify.text =
//                    binding.root.resources.getString(R.string.yes_mission_certify_write)
//            } else {
//                binding.btnCertify.icon =
//                    ContextCompat.getDrawable(binding.root.context, R.drawable.ic_check)
//                binding.btnCertify.text =
//                    binding.root.resources.getString(R.string.yes_mission_certify)
//            }
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
        private val diffUtil = object : DiffUtil.ItemCallback<ResponseGroupMissionData.Data>() {
            override fun areItemsTheSame(
                oldItem: ResponseGroupMissionData.Data,
                newItem: ResponseGroupMissionData.Data,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ResponseGroupMissionData.Data,
                newItem: ResponseGroupMissionData.Data,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}