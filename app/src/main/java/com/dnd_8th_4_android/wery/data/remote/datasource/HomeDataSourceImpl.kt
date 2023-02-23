package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.HomeService
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(private val homeService: HomeService): HomeDataSource {

    override suspend fun signGroup(): ResponseGroupData {
        return homeService.signGroup()
    }

    override suspend fun allGroupPost(groupId: String, page: Int): ResponsePostData {
        return homeService.allGroupPost(groupId, page)
    }

    override suspend fun sendEmotionData(contentId: Int, body: RequestEmotionStatus): ResponseEmotionData {
        return homeService.sendEmotion(contentId, body)
    }

    override suspend fun groupPostSearch(
        groupId: String,
        word: String,
        page: Int,
    ): ResponsePostData {
        return homeService.groupPostSearch(groupId, word, page)
    }
}