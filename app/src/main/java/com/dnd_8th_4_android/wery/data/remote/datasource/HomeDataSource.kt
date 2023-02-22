package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData

interface HomeDataSource {

    suspend fun signGroup(): ResponseGroupData

    suspend fun allGroupPost(
        groupId: String,
        page: Int,
    ): ResponsePostData
}