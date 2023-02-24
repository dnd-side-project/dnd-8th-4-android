package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.DetailService
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.detail.RequestPostDetailCommentNote
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseEmotionData
import javax.inject.Inject

class DetailDataSourceImpl @Inject constructor(private val detailService: DetailService): DetailDataSource {

    override suspend fun getEmotion(contentId: Int): ResponsePostDetailEmotionData {
        return detailService.getEmotion(contentId)
    }

    override suspend fun getComment(contentId: Int, page: Int): ResponsePostDetailCommentData {
        return detailService.getComment(contentId, page)
    }

    override suspend fun sendEmotionData(contentId: Int, body: RequestEmotionStatus): ResponseEmotionData {
        return detailService.sendEmotion(contentId, body)
    }

    override suspend fun sendContent(
        contentId: Int,
        body: RequestPostDetailCommentNote,
    ): BaseResponse {
        return detailService.sendContent(contentId, body)
    }
}