package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseSetBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData

interface GroupRepository {

    suspend fun getBookmarkList(): ResponseBookmarkData

    suspend fun signGroup(): ResponseGroupData

    suspend fun setBookmark(
        groupId: Int
    ): ResponseSetBookmarkData

    suspend fun allGroupPost(
        groupId: Int,
        page: Int,
    ): ResponsePostData

    suspend fun sendEmotionData(
        contentId: Int,
        body: RequestEmotionStatus,
    ): ResponseEmotionData
}