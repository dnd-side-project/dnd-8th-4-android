package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseGroupList

interface PostDataSource {
    suspend fun getGroupList(): Result<ResponseGroupList>
}