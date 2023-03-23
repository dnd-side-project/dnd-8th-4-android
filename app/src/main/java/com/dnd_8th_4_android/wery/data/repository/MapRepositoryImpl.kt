package com.dnd_8th_4_android.wery.data.repository

import ResponseMapFeedList
import com.dnd_8th_4_android.wery.data.remote.datasource.MapDataSource
import com.dnd_8th_4_android.wery.data.remote.model.map.RequestMapMissionList
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapFeedData
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapMissionData
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapMissionList
import com.dnd_8th_4_android.wery.domain.repository.MapRepository
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(private val mapDataSource: MapDataSource) :
    MapRepository {
    override suspend fun getMapMissionList(body: RequestMapMissionList): ResponseMapMissionList {
        return mapDataSource.getMapMissionList(body)
    }

    override suspend fun getMissionData(missionId: Int): ResponseMapMissionData {
        return mapDataSource.getMapMissionDetail(missionId)
    }

    override suspend fun getMapFeedList(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double,
    ): ResponseMapFeedList {
        return mapDataSource.getFeedList(
            startLatitude,
            startLatitude,
            endLatitude,
            endLongitude,
        )
    }

    override suspend fun getFeedData(location: String): ResponseMapFeedData {
        return mapDataSource.getMapFeedDetail(location)
    }
}