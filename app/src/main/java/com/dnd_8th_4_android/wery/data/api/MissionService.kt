package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.mission.*
import retrofit2.http.*

interface MissionService {

    @GET("/sticker/main")
    suspend fun getMissionStatus(): ResponseSticker

    @GET("/mission/list/main")
    suspend fun getMyMissionList(): ResponseMyMissionList

    @GET("/mission/list/main")
    suspend fun getMainMissionCardList(): ResponseMainMissionCard

    @GET("/mission")
    suspend fun getMissionDetail(
        @Query("missionId") missionId: Int,
    ): ResponseMissionDetailData

    @DELETE("/mission")
    suspend fun missionDelete(
        @Query("missionId") missionId: Int,
    ): BaseResponse

    @POST("/mission")
    suspend fun createMission(
        @Body body: RequestCreateMissionData
    ): BaseResponse
}