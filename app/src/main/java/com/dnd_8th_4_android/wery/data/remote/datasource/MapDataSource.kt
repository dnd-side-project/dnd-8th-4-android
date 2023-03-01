package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.map.RequestMapMissionList
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapMissionList

interface MapDataSource {
    suspend fun getMapMissionList(body: RequestMapMissionList): ResponseMapMissionList
}