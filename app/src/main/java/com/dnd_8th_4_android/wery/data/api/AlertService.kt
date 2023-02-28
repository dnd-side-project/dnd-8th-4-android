package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertInviteData
import retrofit2.http.GET

interface AlertService {

    @GET("/notification")
    suspend fun getInvite(): ResponseAlertInviteData
}