package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.HomeDataSource
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeDataSource: HomeDataSource) :
    HomeRepository {

    override suspend fun signGroup(): ResponseGroupData {
        return homeDataSource.signGroup()
    }

    override suspend fun allGroupPost(groupId: String, page: Int): ResponsePostData {
        return homeDataSource.allGroupPost(groupId, page)
    }

    override suspend fun sendEmotionData(contentId: Int, body: RequestEmotionStatus): BaseResponse {
        return homeDataSource.sendEmotionData(contentId, body)
    }
}