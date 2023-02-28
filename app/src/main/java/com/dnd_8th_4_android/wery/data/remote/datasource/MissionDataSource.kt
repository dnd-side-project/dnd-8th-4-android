package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMyMissionList

interface MissionDataSource {
    suspend fun getMyMissionList(): ResponseMyMissionList
}