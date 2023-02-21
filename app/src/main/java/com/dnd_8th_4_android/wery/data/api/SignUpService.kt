package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.sign.RequestSignUpData
import com.dnd_8th_4_android.wery.data.remote.model.sign.ResponseSignUpData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {

    @POST("/auth")
    suspend fun signUp(
        @Body body: RequestSignUpData
    ): ResponseSignUpData
}