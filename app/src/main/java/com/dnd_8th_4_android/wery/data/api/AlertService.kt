package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertInviteData
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertNotificationData
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertPostInfoData
import retrofit2.http.GET
import retrofit2.http.Query

interface AlertService {

    @GET("/notification")
    suspend fun getInvite(): ResponseAlertInviteData

    @GET("/group/invite/accept")
    suspend fun setAccept(
        @Query("groupId") groupId: Int,
        @Query("notificationId") notificationId: Int,
    ): BaseResponse

    @GET("/group/invite/reject")
    suspend fun setDeny(
        @Query("groupId") groupId: Int,
        @Query("notificationId") notificationId: Int,
    ): BaseResponse

    @GET("/notification/all")
    suspend fun getNotificationList(): ResponseAlertNotificationData

    @GET("/notification/read")
    suspend fun readAlert(
        @Query("notificationId") notificationId: Int
    ): BaseResponse

    @GET("/content")
    suspend fun getAlertPostInfo(
        @Query("contentId")contentId:Int
    ):ResponseAlertPostInfoData
}