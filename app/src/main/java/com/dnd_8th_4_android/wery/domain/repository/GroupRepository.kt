package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.group.*
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.data.remote.model.mission.RequestMissionCertifyData
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMissionCertifyData
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface GroupRepository {

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

    suspend fun getUserSearchList(
        keyword: String,
    ): ResponseUserSearchData

    suspend fun groupInvite(
        body: RequestGroupInviteData
    ): BaseResponse

    suspend fun createGroup(
        data: HashMap<String, RequestBody>,
        image: MultipartBody.Part?
    ): Result<BaseResponse>

    suspend fun modifyGroup(
        data: HashMap<String, RequestBody>,
        image: MultipartBody.Part?
    ): Result<BaseResponse>

    suspend fun missionCertify(
        body: RequestMissionCertifyData
    ): ResponseMissionCertifyData
}