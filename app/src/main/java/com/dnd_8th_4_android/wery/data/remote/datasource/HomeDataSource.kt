package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData

interface HomeDataSource {

    suspend fun signGroup(): ResponseGroupData
}