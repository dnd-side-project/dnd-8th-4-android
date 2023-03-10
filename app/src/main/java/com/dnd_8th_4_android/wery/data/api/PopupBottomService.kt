package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.ResponsePopupBottomData
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query

interface PopupBottomService {

    @GET("/bookmark")
    suspend fun setBookmark(
        @Query("contentId") contentId: Int
    ): ResponsePopupBottomData

    @DELETE("/content")
    suspend fun setPostDelete(
        @Query("contentId") contentId: Int
    ): BaseResponse
}