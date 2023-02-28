package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.mission.*
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseGroupList

interface MissionRepository {

    suspend fun getMissionStatus(): ResponseSticker

    suspend fun getMyMissionList(): ResponseMyMissionList
    suspend fun getMainMissionList(): ResponseMainMissionCard

    suspend fun getMissionDetail(
        missionId: Int,
    ): ResponseMissionDetailData

    suspend fun missionDelete(
        missionId: Int,
    ): BaseResponse

    suspend fun createMission(
        body:RequestCreateMissionData
    ): BaseResponse

    suspend fun getMyGroupList(): Result<ResponseGroupList>

    suspend fun missionCertify(
        body: RequestMissionCertifyData
    ): ResponseMissionCertifyData

}