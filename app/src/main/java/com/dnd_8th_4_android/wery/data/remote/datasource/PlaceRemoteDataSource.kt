package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseSearchPlace


interface PlaceRemoteDataSource {
    suspend fun searchPlace(query: String): Result<ResponseSearchPlace>
}