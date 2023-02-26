package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseGroupList
import retrofit2.Response
import retrofit2.http.GET

interface PostService {

    @GET("/group/list/my")
    suspend fun getMyGroupList(): Response<ResponseGroupList>
}