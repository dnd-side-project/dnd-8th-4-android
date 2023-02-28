package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertInviteData

interface AlertDataSource {

    suspend fun getInvite(): ResponseAlertInviteData
}