package com.dnd_8th_4_android.wery.data.remote.model.datasource

import com.dnd_8th_4_android.wery.data.api.PlaceService
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseSearchPlace
import javax.inject.Inject


class PlaceRemoteDataSource @Inject constructor(private val placeService: PlaceService) {
    suspend fun searchPlace(authorization: String, query: String): Result<ResponseSearchPlace> {
        val response = placeService.searchPlace(authorization, query)
        if (response.isSuccessful) {
            response.body()?.let { return Result.success(it) }
        }
        return Result.failure(IllegalStateException(response.message()))
    }
}