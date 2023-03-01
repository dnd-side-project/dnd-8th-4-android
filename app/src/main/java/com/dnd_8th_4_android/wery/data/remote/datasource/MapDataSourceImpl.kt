package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.MapService
import com.dnd_8th_4_android.wery.data.remote.model.map.RequestMapMissionList
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapMissionList
import javax.inject.Inject

class MapDataSourceImpl @Inject constructor(private val mapService: MapService) :
    MapDataSource {

    override suspend fun getMapMissionList(body: RequestMapMissionList): ResponseMapMissionList {
        return mapService.getMissionList(body)
    }
}