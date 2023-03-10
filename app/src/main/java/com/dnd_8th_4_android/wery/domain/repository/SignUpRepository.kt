package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.sign.RequestSignUpData
import com.dnd_8th_4_android.wery.data.remote.model.sign.ResponseSignUpData

interface SignUpRepository {

    suspend fun emailCheck(
        email: String,
    ): BaseResponse

    suspend fun signUp(
        body: RequestSignUpData,
    ): ResponseSignUpData
}