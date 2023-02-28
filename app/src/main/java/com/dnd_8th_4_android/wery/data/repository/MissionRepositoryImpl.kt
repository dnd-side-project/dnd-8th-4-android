package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.MissionDataSource
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMissionDetailData
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMyMissionList
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseSticker
import com.dnd_8th_4_android.wery.domain.repository.MissionRepository
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(private val missionDataSource: MissionDataSource): MissionRepository {

    override suspend fun getMissionStatus(): ResponseSticker {
        return missionDataSource.getMissionStatus()
    }

    override suspend fun getMyMissionList(): ResponseMyMissionList {
        return missionDataSource.getMyMissionList()
    }

    override suspend fun getMissionDetail(missionId: Int): ResponseMissionDetailData {
        return missionDataSource.getMissionDetail(missionId)
    }

    override suspend fun missionDelete(missionId: Int): BaseResponse {
        return missionDataSource.missionDelete(missionId)
    }
}