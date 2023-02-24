package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.DetailDataSource
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import com.dnd_8th_4_android.wery.domain.repository.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(private val detailDataSource: DetailDataSource) :
    DetailRepository {

    override suspend fun getComment(contentId: Int): ResponsePostDetailEmotionData {
        return detailDataSource.getEmotion(contentId)
    }
}