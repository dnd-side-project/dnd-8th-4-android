package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.SignUpDataSource
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.sign.RequestSignUpData
import com.dnd_8th_4_android.wery.data.remote.model.sign.ResponseSignUpData
import com.dnd_8th_4_android.wery.domain.repository.SignUpRepository
import com.dnd_8th_4_android.wery.presentation.di.HttpClient
import javax.inject.Inject

class SignUpRepositoryImpl@Inject constructor(private val signUpDataSource: SignUpDataSource) : SignUpRepository {

    override suspend fun emailCheck(email: String): BaseResponse {
        return signUpDataSource.emailCheck(email)
    }

    override suspend fun signUp(body: RequestSignUpData): ResponseSignUpData {
        return signUpDataSource.signUp(body)
    }
}