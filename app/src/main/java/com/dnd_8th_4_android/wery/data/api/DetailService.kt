package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.detail.RequestPostDetailCommentNote
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseEmotionData
import retrofit2.http.*

interface DetailService {

    @GET("content/{contentId}/emotion")
    suspend fun getEmotion(
        @Path("contentId") contentId: Int,
    ): ResponsePostDetailEmotionData

    @GET("content/{contentId}/comment")
    suspend fun getComment(
        @Path("contentId") contentId: Int,
        @Query("page") page: Int,
    ): ResponsePostDetailCommentData

    @POST("/content/{contentId}/emotion")
    suspend fun sendEmotion(
        @Path("contentId") contentId: Int,
        @Body body: RequestEmotionStatus,
    ): ResponseEmotionData

    @POST("/content/{contentId}/comment")
    suspend fun sendContent(
        @Path("contentId") contentId: Int,
        @Body body: RequestPostDetailCommentNote
    ): BaseResponse
}