package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseSearchPlace

interface PlaceRepository {
    suspend fun searchPlace(authorization: String, query: String): Result<ResponseSearchPlace>
}
