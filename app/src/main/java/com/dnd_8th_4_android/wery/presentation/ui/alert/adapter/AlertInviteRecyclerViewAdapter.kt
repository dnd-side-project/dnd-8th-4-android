package com.dnd_8th_4_android.wery.presentation.ui.alert.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.alert.RequestAlertInviteData
import com.dnd_8th_4_android.wery.databinding.ItemAlertInviteBinding

class AlertInviteRecyclerViewAdapter :
    ListAdapter<RequestAlertInviteData.Data.NotificationInfoList, AlertInviteRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemAlertInviteBinding

    class ViewHolder(private val binding: ItemAlertInviteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RequestAlertInviteData.Data.NotificationInfoList) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemAlertInviteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffUtil =
            object : DiffUtil.ItemCallback<RequestAlertInviteData.Data.NotificationInfoList>() {
                override fun areItemsTheSame(
                    oldItem: RequestAlertInviteData.Data.NotificationInfoList,
                    newItem: RequestAlertInviteData.Data.NotificationInfoList,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: RequestAlertInviteData.Data.NotificationInfoList,
                    newItem: RequestAlertInviteData.Data.NotificationInfoList,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}