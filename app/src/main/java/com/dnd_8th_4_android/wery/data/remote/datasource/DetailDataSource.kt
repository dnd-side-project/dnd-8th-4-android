package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData

interface DetailDataSource {

    suspend fun getEmotion(
        contentId: Int,
    ): ResponsePostDetailEmotionData
}