package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMainMissionCard
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMyMissionList
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseSticker

interface MissionDataSource {

    suspend fun getMissionStatus(): ResponseSticker

    suspend fun getMyMissionList(): ResponseMyMissionList

    suspend fun getMainMissionList(): ResponseMainMissionCard
}