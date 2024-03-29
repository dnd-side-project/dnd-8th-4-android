package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.AlertDataSource
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertInviteData
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertNotificationData
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertPostInfoData
import com.dnd_8th_4_android.wery.domain.repository.AlertRepository
import javax.inject.Inject

class AlertRepositoryImpl @Inject constructor(private val alertDataSource: AlertDataSource): AlertRepository {

    override suspend fun getInvite(): ResponseAlertInviteData {
        return alertDataSource.getInvite()
    }

    override suspend fun setAccept(groupId: Int, notificationId: Int): BaseResponse {
        return alertDataSource.setAccept(groupId, notificationId)
    }

    override suspend fun setDeny(groupId: Int, notificationId: Int): BaseResponse {
        return alertDataSource.setDeny(groupId, notificationId)
    }

    override suspend fun getNotificationList(): ResponseAlertNotificationData {
        return alertDataSource.getNotificationList()
    }

    override suspend fun readAlert(notificationId: Int): BaseResponse {
        return alertDataSource.readAlert(notificationId)
    }

    override suspend fun getAlertPostInfo(contentId: Int): ResponseAlertPostInfoData {
        return alertDataSource.getAlertPostInfo(contentId)
    }
}