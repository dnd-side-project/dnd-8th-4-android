package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData

interface DetailRepository {

    suspend fun getEmotion(
        contentId: Int,
    ): ResponsePostDetailEmotionData

    suspend fun getComment(
        contentId: Int,
        page: Int,
    ): ResponsePostDetailCommentData
}