package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMainMissionCard
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.mission.*

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

    suspend fun missionCertify(
        body: RequestMissionCertifyData
    ): ResponseMissionCertifyData
}