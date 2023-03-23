package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.MapDataSource
import com.dnd_8th_4_android.wery.data.remote.model.map.*
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
        page: Int
    ): ResponseMapFeedLis {
        return mapDataSource.getFeedList(
            startLatitude,
            startLatitude,
            endLatitude,
            endLongitude,
            page
        )
    }

    override suspend fun getFeedData(location: String): ResponseMapFeedData {
        return mapDataSource.getMapFeedDetail(location)
    }
}