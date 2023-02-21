package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.SignUpService
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.sign.RequestSignUpData
import com.dnd_8th_4_android.wery.data.remote.model.sign.ResponseSignUpData
import com.dnd_8th_4_android.wery.presentation.di.HttpClient
import javax.inject.Inject

class SignUpDataSourceImpl @Inject constructor(@HttpClient private val signUpService: SignUpService) :
    SignUpDataSource {

    override suspend fun emailCheck(email: String): BaseResponse {
        return signUpService.emailCheck(email)
    }

    override suspend fun signUp(body: RequestSignUpData): ResponseSignUpData {
        return signUpService.signUp(body)
    }
}