package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.detail.*
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus

interface DetailRepository {

    suspend fun getPostDetail(
        contentId: Int,
    ): ResponsePostDetailData

    suspend fun getEmotion(
        contentId: Int,
    ): ResponsePostDetailEmotionData

    suspend fun getComment(
        contentId: Int,
        page: Int,
    ): ResponsePostDetailCommentData

    suspend fun sendEmotionData(
        contentId: Int,
        body: RequestEmotionStatus,
    ): BaseResponse

    suspend fun sendContent(
        contentId: Int,
        body: RequestPostDetailCommentNote,
    ): BaseResponse

    suspend fun sendSticker(
        contentId: Int,
        body: RequestPostDetailStickerId,
    ): BaseResponse

    suspend fun getSticker(): ResponsePostDetailStickerData
}