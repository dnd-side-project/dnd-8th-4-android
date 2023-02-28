package com.dnd_8th_4_android.wery.presentation.ui.alert.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertInviteData
import com.dnd_8th_4_android.wery.databinding.ItemAlertInviteBinding
import com.dnd_8th_4_android.wery.presentation.ui.detail.adapter.PostDetailStickerRecyclerViewAdapter

class AlertInviteRecyclerViewAdapter :
    ListAdapter<ResponseAlertInviteData.Data.NotificationInfoList, AlertInviteRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {
    private lateinit var binding: ItemAlertInviteBinding
    private lateinit var onAcceptClickListener: OnAcceptClickListener
    private lateinit var onDenyClickListener: OnDenyClickListener

    inner class ViewHolder(private val binding: ItemAlertInviteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResponseAlertInviteData.Data.NotificationInfoList) {
            binding.ivGroupImage.clipToOutline = true
            Glide.with(binding.ivGroupImage.context).load(item.groupImageUrl)
                .into(binding.ivGroupImage)

            binding.tvGroupName.text = item.groupName
            binding.tvGroupContent.text = item.groupNote
            binding.tvTime.text = item.groupInvitedAt

            if (item.readYn) {
                binding.btnGroupParticipate.isEnabled = false
                binding.btnGroupDeny.isEnabled = false
            } else {
                binding.btnGroupParticipate.isEnabled = true
                binding.btnGroupDeny.isEnabled = true
            }

            binding.btnGroupParticipate.setOnClickListener {
                onAcceptClickListener.onClicked(item.groupId, item.notificationId)
            }

            binding.btnGroupDeny.setOnClickListener {
                onDenyClickListener.onClicked(item.groupId, item.notificationId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemAlertInviteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    fun setOnAcceptClickListener(listener: (Int, Int) -> Unit) {
        onAcceptClickListener = object : OnAcceptClickListener {
            override fun onClicked(groupId: Int, notificationId: Int) {
                listener(groupId, notificationId)
            }
        }
    }

    interface OnAcceptClickListener {
        fun onClicked(groupId: Int, notificationId: Int)
    }

    fun setOnDenyClickListener(listener: (Int, Int) -> Unit) {
        onDenyClickListener = object : OnDenyClickListener {
            override fun onClicked(groupId: Int, notificationId: Int) {
                listener(groupId, notificationId)
            }
        }
    }

    interface OnDenyClickListener {
        fun onClicked(groupId: Int, notificationId: Int)
    }

    companion object {
        private val diffUtil =
            object : DiffUtil.ItemCallback<ResponseAlertInviteData.Data.NotificationInfoList>() {
                override fun areItemsTheSame(
                    oldItem: ResponseAlertInviteData.Data.NotificationInfoList,
                    newItem: ResponseAlertInviteData.Data.NotificationInfoList,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ResponseAlertInviteData.Data.NotificationInfoList,
                    newItem: ResponseAlertInviteData.Data.NotificationInfoList,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}