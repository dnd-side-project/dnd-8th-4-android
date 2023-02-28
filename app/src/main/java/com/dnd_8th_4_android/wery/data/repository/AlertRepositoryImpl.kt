package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.AlertDataSource
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertInviteData
import com.dnd_8th_4_android.wery.domain.repository.AlertRepository
import javax.inject.Inject

class AlertRepositoryImpl @Inject constructor(private val alertDataSource: AlertDataSource): AlertRepository {

    override suspend fun getInvite(): ResponseAlertInviteData {
        return alertDataSource.getInvite()
    }

    override suspend fun setAccept(groupId: Int, notificationId: Int): BaseResponse {
        return alertDataSource.setAccept(groupId, notificationId)
    }
}