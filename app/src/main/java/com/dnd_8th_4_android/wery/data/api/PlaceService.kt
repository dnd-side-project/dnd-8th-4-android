package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseSearchPlace
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PlaceService {

    companion object {
        const val BASE_URL = "https://dapi.kakao.com/v2/"
    }

    @GET("local/search/keyword.json")
    suspend fun searchPlace(
        @Header("Authorization") authorization: String = "KakaoAK 577bae1f4d5f3e349cb0b4c286bfa7a1",
        @Query("query") query: String
    ): Response<ResponseSearchPlace>

}