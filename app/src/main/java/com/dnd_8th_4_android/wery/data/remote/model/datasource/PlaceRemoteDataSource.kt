package com.dnd_8th_4_android.wery.data.remote.model.datasource

import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseSearchPlace


interface PlaceRemoteDataSource {
    suspend fun searchPlace(query: String): Result<ResponseSearchPlace>
}