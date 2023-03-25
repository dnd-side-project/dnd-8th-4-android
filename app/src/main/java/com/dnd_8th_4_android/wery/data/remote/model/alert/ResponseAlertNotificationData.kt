package com.dnd_8th_4_android.wery.data.remote.model.alert

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseAlertNotificationData(
    val data: Data,
) : BaseResponse() {

    data class Data(
        val notificationData: List<NotificationInfo>,
        val totalCount: Int
    )

    data class NotificationInfo(
        val commentId: Int,
        val commentLikeId: Int,
        val commentNote: String,
        val contentId: Int,
        val createdAt: String,
        val emotionId: Int,
        val emotionStatus: Int,
        val groupId: Int,
        val groupImageUrl: String,
        val groupName: String,
        val groupNote: String,
        val notificationId: Int,
        val notificationType: String,
        val readYn: Boolean,
        val stickerId: Int,
        val userName: String,
        val userProfileImageUrl: String
    )
}