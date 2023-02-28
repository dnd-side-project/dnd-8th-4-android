package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMyMissionList

interface MissionRepository {
    suspend fun getMyMissionList(): ResponseMyMissionList
}