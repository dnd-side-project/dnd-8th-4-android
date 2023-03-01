package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.map.RequestMapMissionList
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapMissionData
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapMissionList

interface MapRepository {
    suspend fun getMapMissionList(
        body: RequestMapMissionList
    ): ResponseMapMissionList

    suspend fun getMissionData(
        missionId: Int
    ): ResponseMapMissionData
}