package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupInformationData
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupMissionData
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseSetBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData

interface GroupDataSource {

    suspend fun getBookmarkList(): ResponseBookmarkData

    suspend fun signGroup(): ResponseGroupData

    suspend fun setBookmark(
        groupId: Int,
    ): ResponseSetBookmarkData

    suspend fun allGroupPost(
        groupId: Int,
        page: Int,
    ): ResponsePostData

    suspend fun sendEmotionData(
        contentId: Int,
        body: RequestEmotionStatus,
    ): ResponseEmotionData

    suspend fun getMission(
        groupId: Int,
    ): ResponseGroupMissionData

    suspend fun getGroupInformation(
        groupId: Int,
    ): ResponseGroupInformationData

    suspend fun deleteGroup(
        groupId: Int,
    ): BaseResponse
}