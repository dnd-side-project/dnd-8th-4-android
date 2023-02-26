package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseGroupList

interface PostRepository {
    suspend fun getMyGroupList(): Result<ResponseGroupList>
}
