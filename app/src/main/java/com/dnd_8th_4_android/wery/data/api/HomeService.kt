package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {

    @GET("/group/list")
    suspend fun signGroup(): ResponseGroupData

    @GET("/content/group/all")
    suspend fun allGroupPost(
        @Query("groupId") groupId: Int,
        @Query("page") page: Int,
    ): ResponsePostData
}