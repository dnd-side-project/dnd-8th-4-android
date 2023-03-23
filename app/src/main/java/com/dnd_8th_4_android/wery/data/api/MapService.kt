package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.map.*
import retrofit2.http.*

interface MapService {
    @POST("/mission/list/map")
    suspend fun getMissionList(@Body body: RequestMapMissionList): ResponseMapMissionList

    @GET("/mission/map")
    suspend fun getMissionData(@Query("missionId") missionId: Int): ResponseMapMissionData

    @GET("/content/map")
    suspend fun getFeedList(
        @Query("startLatitude") startLatitude: Double,
        @Query("startLongitude") startLongitude: Double,
        @Query("endLatitude") endLatitude: Double,
        @Query("endLongitude") endLongitude: Double,
        @Query("page") page: Int? = 1,
    ): ResponseMapFeedLis

    @GET("/content/map/detail")
    suspend fun getFeedData(
        @Query("location") location: String
    ): ResponseMapFeedData
}