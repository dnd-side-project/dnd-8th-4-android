package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.HomeDataSource
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.domain.repository.HomeRepository

class HomeRepositoryImpl(private val homeDataSource: HomeDataSource): HomeRepository {

    override suspend fun signGroup(): ResponseGroupData {
        return homeDataSource.signGroup()
    }
}