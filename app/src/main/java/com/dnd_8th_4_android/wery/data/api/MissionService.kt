package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMainMissionCard
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMyMissionList
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseSticker
import retrofit2.http.GET

interface MissionService {

    @GET("/sticker/main")
    suspend fun getMissionStatus(): ResponseSticker

    @GET("/mission/list/main")
    suspend fun getMyMissionList(): ResponseMyMissionList

    @GET("/mission/list/main")
    suspend fun getMainMissionCardList(): ResponseMainMissionCard
}