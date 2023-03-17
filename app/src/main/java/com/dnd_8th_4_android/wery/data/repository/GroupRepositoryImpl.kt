package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.GroupDataSource
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.group.*
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.data.remote.model.mission.RequestMissionCertifyData
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMissionCertifyData
import com.dnd_8th_4_android.wery.domain.repository.GroupRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(private val groupDataSource: GroupDataSource) :
    GroupRepository {

    override suspend fun getBookmarkList(): ResponseBookmarkData {
        return groupDataSource.getBookmarkList()
    }

    override suspend fun signGroup(): ResponseGroupData {
        return groupDataSource.signGroup()
    }

    override suspend fun setBookmark(groupId: Int): ResponseSetBookmarkData {
        return groupDataSource.setBookmark(groupId)
    }

    override suspend fun allGroupPost(groupId: Int, page: Int): ResponsePostData {
        return groupDataSource.allGroupPost(groupId, page)
    }

    override suspend fun sendEmotionData(
        contentId: Int,
        body: RequestEmotionStatus,
    ): BaseResponse {
        return groupDataSource.sendEmotionData(contentId, body)
    }

    override suspend fun getMission(groupId: Int): ResponseGroupMissionData {
        return groupDataSource.getMission(groupId)
    }

    override suspend fun getGroupInformation(groupId: Int): ResponseGroupInformationData {
        return groupDataSource.getGroupInformation(groupId)
    }

    override suspend fun deleteGroup(groupId: Int): BaseResponse {
        return groupDataSource.deleteGroup(groupId)
    }

    override suspend fun getUserSearchList(keyword: String): ResponseUserSearchData {
        return groupDataSource.getUserSearchList(keyword)
    }

    override suspend fun groupInvite(body: RequestGroupInviteData): BaseResponse {
        return groupDataSource.groupInvite(body)
    }

    override suspend fun createGroup(
        data: HashMap<String, RequestBody>,
        image: MultipartBody.Part?,
    ): Result<BaseResponse> {
        return groupDataSource.createGroup(data, image)
    }

    override suspend fun modifyGroup(
        data: HashMap<String, RequestBody>,
        image: MultipartBody.Part?,
    ): Result<BaseResponse> {
        return groupDataSource.modifyGroup(data, image)
    }

    override suspend fun missionCertify(body: RequestMissionCertifyData): ResponseMissionCertifyData {
        return groupDataSource.missionCertify(body)
    }
}