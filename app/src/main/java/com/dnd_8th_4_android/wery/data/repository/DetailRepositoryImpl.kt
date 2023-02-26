package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.DetailDataSource
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.detail.RequestPostDetailCommentNote
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseEmotionData
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

    override suspend fun sendEmotionData(contentId: Int, body: RequestEmotionStatus): ResponseEmotionData {
        return detailDataSource.sendEmotionData(contentId, body)
    }

    override suspend fun sendContent(
        contentId: Int,
        body: RequestPostDetailCommentNote,
    ): BaseResponse {
        return detailDataSource.sendContent(contentId, body)
    }
}