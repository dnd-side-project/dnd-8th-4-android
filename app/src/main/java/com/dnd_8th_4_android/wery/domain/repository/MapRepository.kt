package com.dnd_8th_4_android.wery.domain.repository

import ResponseMapFeedList
import com.dnd_8th_4_android.wery.data.remote.model.map.*

interface MapRepository {
    suspend fun getMapMissionList(
        body: RequestMapMissionList
    ): ResponseMapMissionList

    suspend fun getMissionData(
        missionId: Int
    ): ResponseMapMissionData

    suspend fun getMapFeedList(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double,
    ): ResponseMapFeedList

    suspend fun getFeedData(
        location: String
    ): ResponseMapFeedData
}