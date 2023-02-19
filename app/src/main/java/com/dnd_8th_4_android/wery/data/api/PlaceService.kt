package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.BuildConfig
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseSearchPlace
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface PlaceService {
    @GET
    suspend fun searchPlace(
        @Header("Authorization") authorization: String = BuildConfig.KAKAO_API_KEY,
        @Url url: String = "https://dapi.kakao.com/v2/local/search/keyword.json",
        @Query("query") query: String
    ): Response<ResponseSearchPlace>
}