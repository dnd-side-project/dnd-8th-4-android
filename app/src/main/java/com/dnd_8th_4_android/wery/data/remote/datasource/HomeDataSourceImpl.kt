package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.HomeService
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(private val homeService: HomeService): HomeDataSource {

    override suspend fun signGroup(): ResponseGroupData {
        return homeService.signGroup()
    }
}