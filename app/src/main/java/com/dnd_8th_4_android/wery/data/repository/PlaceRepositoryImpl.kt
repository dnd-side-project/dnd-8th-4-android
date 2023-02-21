package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.PlaceRemoteDataSource
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseSearchPlace
import com.dnd_8th_4_android.wery.domain.repository.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl (private val placeRemoteDataSource: PlaceRemoteDataSource) :
    PlaceRepository {
    override suspend fun searchPlace(
        query: String
    ): Result<ResponseSearchPlace> {
        return placeRemoteDataSource.searchPlace(query = query)
    }
}