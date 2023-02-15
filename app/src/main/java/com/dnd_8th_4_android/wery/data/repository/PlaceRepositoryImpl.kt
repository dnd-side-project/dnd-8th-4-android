package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.model.datasource.PlaceRemoteDataSource
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseSearchPlace
import com.dnd_8th_4_android.wery.domain.repository.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(private val placeRemoteDataSource: PlaceRemoteDataSource) :
    PlaceRepository {
    override suspend fun searchPlace(
        authorization: String,
        query: String
    ): Result<ResponseSearchPlace> {
        return placeRemoteDataSource.searchPlace(authorization = authorization, query = query)
    }
}