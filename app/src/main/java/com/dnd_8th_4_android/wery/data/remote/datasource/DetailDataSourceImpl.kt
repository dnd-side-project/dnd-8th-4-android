package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.DetailService
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import javax.inject.Inject

class DetailDataSourceImpl @Inject constructor(private val detailService: DetailService): DetailDataSource {

    override suspend fun getEmotion(contentId: Int): ResponsePostDetailEmotionData {
        return detailService.getEmotion(contentId)
    }
}