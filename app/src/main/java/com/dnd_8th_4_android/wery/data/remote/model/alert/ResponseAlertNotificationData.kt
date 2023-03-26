package com.dnd_8th_4_android.wery.data.remote.model.alert

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseAlertNotificationData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val notificationInfoList: List<NotificationInfo>
    ) {
        data class NotificationInfo(
            val contentId: Int,
            val commentNote: String?,
            val createdAt: String,
            val groupId: Int,
            val groupImageUrl: String,
            val groupName: String?,
            val groupNote: String?,
            val notificationId: Int,
            val notificationType: String,
            val readYn: Boolean,
            val userName: String,
            val userProfileImageUrl: String
        )
    }
}