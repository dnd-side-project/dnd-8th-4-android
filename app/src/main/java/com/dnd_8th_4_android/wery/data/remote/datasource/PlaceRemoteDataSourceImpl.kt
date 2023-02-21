package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.PlaceService
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseSearchPlace
import com.dnd_8th_4_android.wery.presentation.di.OtherHttpClient
import javax.inject.Inject

class PlaceRemoteDataSourceImpl @Inject constructor(@OtherHttpClient private val placeService: PlaceService) :
    PlaceRemoteDataSource {

    override suspend fun searchPlace(query: String): Result<ResponseSearchPlace> {
        val response = placeService.searchPlace(query=query)
        if (response.isSuccessful) {
            response.body()?.let { return Result.success(it) }
        }
        return Result.failure(IllegalStateException(response.message()))
    }
}