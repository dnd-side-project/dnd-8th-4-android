package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.AlertService
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertInviteData
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertNotificationData
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertPostInfoData
import javax.inject.Inject

class AlertDataSourceImpl @Inject constructor(private val alertService: AlertService): AlertDataSource {

    override suspend fun getInvite(): ResponseAlertInviteData {
        return alertService.getInvite()
    }

    override suspend fun setAccept(groupId: Int, notificationId: Int): BaseResponse {
        return alertService.setAccept(groupId, notificationId)
    }

    override suspend fun setDeny(groupId: Int, notificationId: Int): BaseResponse {
        return alertService.setDeny(groupId, notificationId)
    }

    override suspend fun getNotificationList(): ResponseAlertNotificationData {
        return alertService.getNotificationList()
    }

    override suspend fun readAlert(notificationId: Int): BaseResponse {
        return alertService.readAlert(notificationId)
    }

    override suspend fun getAlertPostInfo(contentId: Int): ResponseAlertPostInfoData {
        return alertService.getAlertPostInfo(contentId)
    }
}