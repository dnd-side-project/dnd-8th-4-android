package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.MissionService
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMainMissionCard
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMyMissionList
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseSticker
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
}