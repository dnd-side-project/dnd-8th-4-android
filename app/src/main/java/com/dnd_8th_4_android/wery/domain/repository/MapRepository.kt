package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.map.*

interface MapRepository {
    suspend fun getMapMissionList(
        body: RequestMapMissionList
    ): ResponseMapMissionList

    suspend fun getMissionData(
        missionId: Int
    ): ResponseMapMissionData

    suspend fun getFeedList(
        x: Double,
        y: Double
    ): ResponseMapFeedList

    suspend fun getFeedData(
        contentId: Int
    ): ResponseMapFeedData
}