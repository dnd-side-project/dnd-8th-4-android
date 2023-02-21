package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.sign.RequestSignUpData
import com.dnd_8th_4_android.wery.data.remote.model.sign.ResponseSignUpData

interface SignUpDataSource {

    suspend fun emailCheck(
        email: String
    ): BaseResponse

    suspend fun signUp(
        body: RequestSignUpData
    ): ResponseSignUpData
}