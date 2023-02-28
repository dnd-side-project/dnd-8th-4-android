package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.MissionDataSource
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseSticker
import com.dnd_8th_4_android.wery.domain.repository.MissionRepository
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(private val missionDataSource: MissionDataSource): MissionRepository {

    override suspend fun getMissionStatus(): ResponseSticker {
        return missionDataSource.getMissionStatus()
    }
}