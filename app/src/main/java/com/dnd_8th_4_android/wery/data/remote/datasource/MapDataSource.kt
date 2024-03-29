package com.dnd_8th_4_android.wery.data.remote.datasource

import ResponseMapFeedList
import com.dnd_8th_4_android.wery.data.remote.model.map.*

interface MapDataSource {
    suspend fun getMapMissionList(body: RequestMapMissionList): ResponseMapMissionList

    suspend fun getMapMissionDetail(missionId: Int): ResponseMapMissionData

    suspend fun getFeedList(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double,
    ): ResponseMapFeedList

    suspend fun getMapFeedDetail(location: String): ResponseMapFeedData
}