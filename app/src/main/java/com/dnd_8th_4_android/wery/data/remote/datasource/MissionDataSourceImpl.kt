package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.MissionService
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.mission.*
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseGroupList
import okhttp3.MultipartBody
import okhttp3.RequestBody

import javax.inject.Inject

class MissionDataSourceImpl @Inject constructor(private val missionService: MissionService): MissionDataSource {

    override suspend fun getMissionStatus(): ResponseSticker {
        return missionService.getMissionStatus()
    }

    override suspend fun getMyMissionList(): ResponseMyMissionList {
        return missionService.getMyMissionList()
    }

    override suspend fun getMainMissionList(): ResponseMainMissionCard {
        return missionService.getMainMissionCardList()
    }

    override suspend fun getMissionDetail(missionId: Int): ResponseMissionDetailData {
        return missionService.getMissionDetail(missionId)
    }

    override suspend fun missionDelete(missionId: Int): BaseResponse {
        return missionService.missionDelete(missionId)
    }

    override suspend fun createMission(body: RequestCreateMissionData): BaseResponse {
        return missionService.createMission(body)
    }

    override suspend fun getGroupList(): Result<ResponseGroupList> {
        val response = missionService.getMyGroupList()
        if (response.isSuccessful) {
            response.body()?.let { return Result.success(it) }
        }
        return Result.failure(IllegalStateException(response.message()))
    }

    override suspend fun missionCertify(body: RequestMissionCertifyData): ResponseMissionCertifyData {
        return missionService.missionCertify(body)
    }

    override suspend fun missionDetail(stickerGroupId: Int): ResponseStickerDetail {
        return missionService.missionDetail(stickerGroupId)
    }

}