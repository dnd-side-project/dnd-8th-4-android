package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.BuildConfig
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseSearchPlace
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface PlaceService {
    @GET("v2/local/search/keyword.json")
    suspend fun searchPlace(
        @Header("Authorization") authorization: String = BuildConfig.KAKAO_API_KEY,
        @Query("query") query: String
    ): Response<ResponseSearchPlace>
}