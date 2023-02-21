package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData

interface HomeRepository {

    suspend fun signGroup(): ResponseGroupData
}