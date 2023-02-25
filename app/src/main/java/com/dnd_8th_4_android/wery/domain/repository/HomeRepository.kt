package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData

interface HomeRepository {

    suspend fun signGroup(): ResponseGroupData

    suspend fun allGroupPost(
        groupId: String,
        page: Int,
    ): ResponsePostData

    suspend fun sendEmotionData(
        contentId: Int,
        body: RequestEmotionStatus,
    ): ResponseEmotionData
}