package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseSearchPlace

interface PlaceRepository {

    suspend fun searchPlace(query: String): Result<ResponseSearchPlace>
}
