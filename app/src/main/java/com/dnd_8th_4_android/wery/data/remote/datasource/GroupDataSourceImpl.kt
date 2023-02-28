package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.GroupService
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.group.*
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class GroupDataSourceImpl @Inject constructor(private val groupService: GroupService) :
    GroupDataSource {

    override suspend fun getBookmarkList(): ResponseBookmarkData {
        return groupService.getBookmarkList()
    }

    override suspend fun signGroup(): ResponseGroupData {
        return groupService.signGroup()
    }

    override suspend fun setBookmark(groupId: Int): ResponseSetBookmarkData {
        return groupService.setBookmark(groupId)
    }

    override suspend fun allGroupPost(groupId: Int, page: Int): ResponsePostData {
        return groupService.allGroupPost(groupId, page)
    }

    override suspend fun sendEmotionData(
        contentId: Int,
        body: RequestEmotionStatus,
    ): ResponseEmotionData {
        return groupService.sendEmotion(contentId, body)
    }

    override suspend fun getMission(groupId: Int): ResponseGroupMissionData {
        return groupService.getMission(groupId)
    }

    override suspend fun getGroupInformation(groupId: Int): ResponseGroupInformationData {
        return groupService.getGroupInformation(groupId)
    }

    override suspend fun deleteGroup(groupId: Int): BaseResponse {
        return groupService.deleteGroup(groupId)
    }

    override suspend fun getUserSearchList(keyword: String): ResponseUserSearchData {
        return groupService.getUserSearchList(keyword)
    }

    override suspend fun groupInvite(body: RequestGroupInviteData): BaseResponse {
        return groupService.groupInvite(body)
    }

    override suspend fun createGroup(
        data: HashMap<String, RequestBody>,
        image: MultipartBody.Part?
    ): Result<BaseResponse> {
        val response = groupService.createGroup(data, image)
        if (response.isSuccessful) {
            response.body()?.let { return Result.success(it) }
        }
        return Result.failure(IllegalStateException(response.message()))
    }
}