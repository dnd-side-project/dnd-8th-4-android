package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface MyPageService {

    @GET("/auth/my/info")
    suspend fun getMyInfo(): ResponseMyInfo

    @Multipart
    @PATCH("/auth")
    suspend fun modifyProfile(
        @PartMap data: HashMap<String, RequestBody>,
        @Part image: MultipartBody.Part?
    ): BaseResponse
}