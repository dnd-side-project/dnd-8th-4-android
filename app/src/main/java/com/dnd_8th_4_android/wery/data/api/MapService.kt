package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.map.RequestMapMissionList
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapMissionList
import retrofit2.http.*

interface MapService {

    @GET("/mission/list/map")
    suspend fun getMissionList(@Body body: RequestMapMissionList): ResponseMapMissionList
}