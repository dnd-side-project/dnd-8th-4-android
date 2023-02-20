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
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseAccessGroupData
import com.dnd_8th_4_android.wery.databinding.ItemYesMissionBinding


class AccessGroupMissionRecyclerViewAdapter :
    ListAdapter<ResponseAccessGroupData.Data, AccessGroupMissionRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemYesMissionBinding

    inner class ViewHolder(private val binding: ItemYesMissionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseAccessGroupData.Data) {

            if (item.day != 1) {
                when (adapterPosition) {
                    1 -> binding.layoutMission.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.color_f47aff
                        )
                    )
                    2 -> binding.layoutMission.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.color_34c18e
                        )
                    )
                    else -> binding.layoutMission.backgroundTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.color_3f75ff
                        )
                    )
                }
                binding.tvRemainDay.text =
                    binding.root.resources.getString(R.string.access_group_day, item.day)

                binding.ivFire.isVisible = item.day in 2..3
            } else {
                binding.tvRemainDay.text =
                    binding.root.resources.getString(R.string.access_group_d_day)
                binding.ivFire.isVisible = true
            }


            binding.tvMissionContent.text = item.content
            binding.tvStartDay.text = item.startDay
            binding.tvEndDay.text = item.endDay

            if (item.isSelected) {
                binding.btnCertify.icon =
                    ContextCompat.getDrawable(binding.root.context, R.drawable.ic_check_write)
                binding.btnCertify.text =
                    binding.root.resources.getString(R.string.yes_mission_certify_write)
            } else {
                binding.btnCertify.icon =
                    ContextCompat.getDrawable(binding.root.context, R.drawable.ic_check)
                binding.btnCertify.text =
                    binding.root.resources.getString(R.string.yes_mission_certify)
            }
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