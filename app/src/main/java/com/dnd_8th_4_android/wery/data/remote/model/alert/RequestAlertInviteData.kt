package com.dnd_8th_4_android.wery.data.remote.model.alert

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class RequestAlertInviteData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val notificationInfoList: MutableList<NotificationInfoList>,
    ) {
        data class NotificationInfoList(
            val notificationId: Int,
            val groupId: Int,
            val groupName: String,
            val groupNote: String,
            val groupImageUrl: String,
            val groupInvitedAt: String,
            val readYn: Boolean,
        )
    }
}