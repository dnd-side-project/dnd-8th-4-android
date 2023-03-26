package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertInviteData
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertNotificationData

interface AlertRepository {

    suspend fun getInvite(): ResponseAlertInviteData

    suspend fun setAccept(
        groupId: Int,
        notificationId: Int,
    ): BaseResponse

    suspend fun setDeny(
        groupId: Int,
        notificationId: Int,
    ): BaseResponse

    suspend fun getNotificationList(): ResponseAlertNotificationData

    suspend fun readAlert(notificationId: Int): BaseResponse
}