package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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
}