package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyInfo
import retrofit2.http.GET

interface MyPageService {

    @GET("/auth/my/info")
    suspend fun getMyInfo(): ResponseMyInfo
}