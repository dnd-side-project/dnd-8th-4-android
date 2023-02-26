package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import retrofit2.http.GET

interface GroupService {

    @GET("group/list/star")
    suspend fun getBookmarkList(): ResponseBookmarkData

    @GET("/group/list")
    suspend fun signGroup(): ResponseGroupData
}