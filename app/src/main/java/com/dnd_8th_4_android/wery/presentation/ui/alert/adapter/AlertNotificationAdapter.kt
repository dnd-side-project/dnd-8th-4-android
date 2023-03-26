package com.dnd_8th_4_android.wery.presentation.ui.alert.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertNotificationData
import com.dnd_8th_4_android.wery.databinding.ItemAlertNotificationCommentBinding
import com.dnd_8th_4_android.wery.databinding.ItemAlertNotificationInviteBinding
import com.dnd_8th_4_android.wery.databinding.ItemAlertNotificationLikeBinding

class AlertNotificationAdapter(private val onItemClick: (ResponseAlertNotificationData.Data.NotificationInfo) -> Unit) :
    ListAdapter<ResponseAlertNotificationData.Data.NotificationInfo, RecyclerView.ViewHolder>(
        diffUtil
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            INVITE_VIEW_TYPE -> AlertNotificationInviteViewHolder(
                ItemAlertNotificationInviteBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), onItemClick
            )

            COMMENT_VIEW_TYPE -> AlertNotificationCommentViewHolder(
                ItemAlertNotificationCommentBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),onItemClick
            )

            LIKE_VIEW_TYPE -> AlertNotificationLikeViewHolder(
                ItemAlertNotificationLikeBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),onItemClick
            )

            else -> throw Exception("unknown type!!")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AlertNotificationInviteViewHolder -> {
                holder.onBind(currentList[position])
                if (position == currentList.lastIndex) holder.binding.viewLine.visibility = View.GONE
            }
            is AlertNotificationCommentViewHolder -> {
                holder.onBind(currentList[position])
                if (position == currentList.lastIndex) holder.binding.viewLine.visibility = View.GONE
            }
            is AlertNotificationLikeViewHolder -> {
                holder.onBind(currentList[position])
                if (position == currentList.lastIndex) holder.binding.viewLine.visibility = View.GONE
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].notificationType) {
            "CONTENT_COMMENT" -> COMMENT_VIEW_TYPE
            "CONTENT_EMOTION" -> COMMENT_VIEW_TYPE
            "COMMENT_LIKE" -> LIKE_VIEW_TYPE
            "NEW_GROUP_MEMBER" -> INVITE_VIEW_TYPE
            else -> throw Exception("unknown type!!")
        }
    }

    override fun getItemCount(): Int = currentList.size

    class AlertNotificationInviteViewHolder(
        val binding: ItemAlertNotificationInviteBinding,
        val onItemClick: (ResponseAlertNotificationData.Data.NotificationInfo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var itemData: ResponseAlertNotificationData.Data.NotificationInfo? = null

        init {
            binding.root.setOnClickListener {
                itemData?.let {
                    onItemClick(it)
                }
            }
        }

        fun onBind(data: ResponseAlertNotificationData.Data.NotificationInfo) {
            itemData = data
            binding.data = data
        }
    }

    class AlertNotificationCommentViewHolder(
        val binding: ItemAlertNotificationCommentBinding,
        val onItemClick: (ResponseAlertNotificationData.Data.NotificationInfo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var itemData: ResponseAlertNotificationData.Data.NotificationInfo? = null

        init {
            binding.root.setOnClickListener {
                itemData?.let {
                    onItemClick(it)
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                itemData?.let {
                    onItemClick(it)
                }
            }
        }

        fun onBind(data: ResponseAlertNotificationData.Data.NotificationInfo) {
            itemData = data
            binding.data = data
        }
    }

    class AlertNotificationLikeViewHolder(
        val binding: ItemAlertNotificationLikeBinding,
        val onItemClick: (ResponseAlertNotificationData.Data.NotificationInfo) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var itemData: ResponseAlertNotificationData.Data.NotificationInfo? = null

        init {
            binding.root.setOnClickListener {
                itemData?.let {
                    onItemClick(it)
                }
            }
        }

        fun onBind(data: ResponseAlertNotificationData.Data.NotificationInfo) {
            itemData = data
            binding.data = data
        }
    }


    companion object {
        const val INVITE_VIEW_TYPE = 1
        const val COMMENT_VIEW_TYPE = 2
        const val LIKE_VIEW_TYPE = 3

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