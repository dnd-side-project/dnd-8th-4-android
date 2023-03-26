package com.dnd_8th_4_android.wery.presentation.ui.alert.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertNotificationData
import com.dnd_8th_4_android.wery.databinding.ItemAlertNotificationTypeImgBinding
import com.dnd_8th_4_android.wery.databinding.ItemAlertNotificationTypeTextBinding

class AlertNotificationAdapter :
    ListAdapter<ResponseAlertNotificationData.Data.NotificationInfo, RecyclerView.ViewHolder>(
        diffUtil
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            IMAGE_VIEW_TYPE -> AlertNotificationImgViewHolder(
                ItemAlertNotificationTypeImgBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            TEXT_VIEW_TYPE -> AlertNotificationTextViewHolder(
                ItemAlertNotificationTypeTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw Exception("unknown type!!")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlertNotificationImgViewHolder -> {
                holder.onBind(currentList[position])
            }
            is AlertNotificationTextViewHolder -> {
                holder.onBind(currentList[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].notificationType) {
            "CONTENT_COMMENT" -> TEXT_VIEW_TYPE
            "CONTENT_EMOTION" -> TEXT_VIEW_TYPE
            "COMMENT_LIKE" -> TEXT_VIEW_TYPE

            "NEW_GROUP_MEMBER" -> IMAGE_VIEW_TYPE
            else -> throw Exception("unknown type!!")
        }
    }

    override fun getItemCount(): Int = currentList.size

    class AlertNotificationImgViewHolder(
        val binding: ItemAlertNotificationTypeImgBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: ResponseAlertNotificationData.Data.NotificationInfo) {
            binding.data = data
        }
    }

    class AlertNotificationTextViewHolder(
        val binding: ItemAlertNotificationTypeTextBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ResponseAlertNotificationData.Data.NotificationInfo) {
            binding.data = data
        }
    }

    companion object {
        const val IMAGE_VIEW_TYPE = 1
        const val TEXT_VIEW_TYPE = 2

        private val diffUtil =
            object : DiffUtil.ItemCallback<ResponseAlertNotificationData.Data.NotificationInfo>() {
                override fun areItemsTheSame(
                    oldItem: ResponseAlertNotificationData.Data.NotificationInfo,
                    newItem: ResponseAlertNotificationData.Data.NotificationInfo,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: ResponseAlertNotificationData.Data.NotificationInfo,
                    newItem: ResponseAlertNotificationData.Data.NotificationInfo,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}