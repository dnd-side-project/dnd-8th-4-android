package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.sign.RequestSignUpData
import com.dnd_8th_4_android.wery.data.remote.model.sign.ResponseSignUpData
import retrofit2.http.*

interface SignUpService {

    @GET("/auth/check")
    suspend fun emailCheck(
        @Query("email") email: String,
    ): BaseResponse

    @POST("/auth")
    suspend fun signUp(
        @Body body: RequestSignUpData,
    ): ResponseSignUpData
}