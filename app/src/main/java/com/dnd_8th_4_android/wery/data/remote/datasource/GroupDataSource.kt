package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData

interface GroupDataSource {

    suspend fun getBookmarkList(): ResponseBookmarkData

    suspend fun signGroup(): ResponseGroupData
}