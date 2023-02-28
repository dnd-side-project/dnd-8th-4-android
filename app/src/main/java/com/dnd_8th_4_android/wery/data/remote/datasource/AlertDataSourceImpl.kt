package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.AlertService
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertInviteData
import javax.inject.Inject

class AlertDataSourceImpl @Inject constructor(private val alertService: AlertService): AlertDataSource {

    override suspend fun getInvite(): ResponseAlertInviteData {
        return alertService.getInvite()
    }

    override suspend fun setAccept(groupId: Int, notificationId: Int): BaseResponse {
        return alertService.setAccept(groupId, notificationId)
    }
}