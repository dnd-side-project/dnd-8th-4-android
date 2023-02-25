package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.search.ResponsePostSearchData
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/content/group/search")
    suspend fun groupPostSearch(
        @Query("groupId") groupId: String,
        @Query("word") word: String,
        @Query("page") page: Int,
    ): ResponsePostSearchData
}