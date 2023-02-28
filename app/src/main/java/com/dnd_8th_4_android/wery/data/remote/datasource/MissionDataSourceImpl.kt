package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.MissionService
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMyMissionList
import javax.inject.Inject

class MissionDataSourceImpl @Inject constructor(private val missionService: MissionService) :
    MissionDataSource {

    override suspend fun getMyMissionList(): ResponseMyMissionList {
        return missionService.getMyMissionList()
    }
}