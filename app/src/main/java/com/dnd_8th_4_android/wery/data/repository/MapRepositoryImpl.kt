package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.MapDataSource
import com.dnd_8th_4_android.wery.data.remote.model.map.RequestMapMissionList
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapMissionList
import com.dnd_8th_4_android.wery.domain.repository.MapRepository
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(private val mapDataSource: MapDataSource) :
    MapRepository {
    override suspend fun getMapMissionList(body: RequestMapMissionList): ResponseMapMissionList {
        return mapDataSource.getMapMissionList(body)
    }
}