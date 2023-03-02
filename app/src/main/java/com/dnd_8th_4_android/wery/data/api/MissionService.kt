package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.mission.*
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseGroupList
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
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

    @GET("/group/list/my")
    suspend fun getMyGroupList(): Response<ResponseGroupList>

    @POST("/mission/check/location")
    suspend fun missionCertify(
        @Body body: RequestMissionCertifyData
    ): ResponseMissionCertifyData

    @GET("/sticker/list/my")
    suspend fun missionDetail(
        @Query("stickerGroupId") stickerGroupId: Int
    ): ResponseStickerDetail
}