package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertInviteData
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertNotificationData
import retrofit2.http.GET

interface AlertDataSource {

    suspend fun getInvite(): ResponseAlertInviteData

    suspend fun setAccept(
        groupId: Int,
        notificationId: Int,
    ): BaseResponse

    suspend fun setDeny(
        groupId: Int,
        notificationId: Int,
    ): BaseResponse

    @GET("/notification/all")
    suspend fun getNotificationList(): ResponseAlertNotificationData
}