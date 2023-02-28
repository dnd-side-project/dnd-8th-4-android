package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMyMissionList
import retrofit2.http.GET

interface MissionService {
    @GET("/mission/list/main")
    suspend fun getMyMissionList(): ResponseMyMissionList
}