package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseBookmarkData
import retrofit2.http.GET

interface GroupService {

    @GET("group/list/star")
    suspend fun getBookmarkList(): ResponseBookmarkData
}