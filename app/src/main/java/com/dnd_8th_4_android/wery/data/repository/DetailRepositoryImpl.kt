package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.DetailDataSource
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.detail.*
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(private val detailDataSource: DetailDataSource) :
    DetailRepository {

    override suspend fun getPostDetail(contentId: Int): ResponsePostDetailData {
        return detailDataSource.getPostDetail(contentId)
    }

    override suspend fun getEmotion(contentId: Int): ResponsePostDetailEmotionData {
        return detailDataSource.getEmotion(contentId)
    }

    override suspend fun getComment(contentId: Int, page: Int): ResponsePostDetailCommentData {
        return detailDataSource.getComment(contentId, page)
    }

    override suspend fun sendEmotionData(contentId: Int, body: RequestEmotionStatus): BaseResponse {
        return detailDataSource.sendEmotionData(contentId, body)
    }

    override suspend fun sendContent(
        contentId: Int,
        body: RequestPostDetailCommentNote,
    ): BaseResponse {
        return detailDataSource.sendContent(contentId, body)
    }

    override suspend fun sendSticker(
        contentId: Int,
        body: RequestPostDetailStickerId,
    ): BaseResponse {
        return detailDataSource.sendSticker(contentId, body)
    }

    override suspend fun getSticker(): ResponsePostDetailStickerData {
        return detailDataSource.getSticker()
    }
}