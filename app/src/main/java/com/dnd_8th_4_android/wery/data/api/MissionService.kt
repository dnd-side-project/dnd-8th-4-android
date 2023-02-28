package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMissionDetailData
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMyMissionList
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseSticker
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface MissionService {

    @GET("/sticker/main")
    suspend fun getMissionStatus(): ResponseSticker

    @GET("/mission/list/main")
    suspend fun getMyMissionList(): ResponseMyMissionList

    @GET("/mission")
    suspend fun getMissionDetail(
        @Query("missionId") missionId: Int,
    ): ResponseMissionDetailData

    @DELETE("/mission")
    suspend fun missionDelete(
        @Query("missionId") missionId: Int,
    ): BaseResponse
}