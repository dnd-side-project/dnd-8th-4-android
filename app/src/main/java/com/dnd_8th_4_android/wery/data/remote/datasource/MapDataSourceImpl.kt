package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.MapService
import com.dnd_8th_4_android.wery.data.remote.model.map.*
import javax.inject.Inject

class MapDataSourceImpl @Inject constructor(private val mapService: MapService) :
    MapDataSource {

    override suspend fun getMapMissionList(body: RequestMapMissionList): ResponseMapMissionList {
        return mapService.getMissionList(body)
    }

    override suspend fun getMapMissionDetail(missionId: Int): ResponseMapMissionData {
        return mapService.getMissionData(missionId)
    }

    override suspend fun getFeedList(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double,
        page: Int
    ): ResponseMapFeedLis {
        return mapService.getFeedList(startLatitude, startLatitude, endLatitude, endLongitude, page)
    }

    override suspend fun getMapFeedDetail(location: String): ResponseMapFeedData {
        return mapService.getFeedData(location)
    }
}