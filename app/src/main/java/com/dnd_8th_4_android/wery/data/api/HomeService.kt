package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import retrofit2.http.*

interface HomeService {

    @GET("/group/list")
    suspend fun signGroup(): ResponseGroupData

    @GET("/content/group/all")
    suspend fun allGroupPost(
        @Query("groupId") groupId: String,
        @Query("page") page: Int,
    ): ResponsePostData

    @POST("/content/{contentId}/emotion")
    suspend fun sendEmotion(
        @Path("contentId") contentId: Int,
        @Body body: RequestEmotionStatus,
    ): ResponseEmotionData

    @GET("/content/group/search")
    suspend fun groupPostSearch(
        @Query("groupId") groupId: String,
        @Query("word") word: String,
        @Query("page") page: Int,
    ): ResponsePostData
}