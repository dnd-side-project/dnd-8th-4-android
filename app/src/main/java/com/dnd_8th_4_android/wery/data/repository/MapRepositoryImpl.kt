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

    override suspend fun getMapFeedList(x: Double, y: Double): ResponseMapFeedList {
        return mapDataSource.getFeedList(x, y)
    }

    override suspend fun getFeedData(location: String): ResponseMapFeedData {
        return mapDataSource.getMapFeedDetail(location)
    }
}