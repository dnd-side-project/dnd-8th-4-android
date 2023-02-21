package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.AuthService
import com.dnd_8th_4_android.wery.data.remote.model.sign.RequestSignInData
import com.dnd_8th_4_android.wery.data.remote.model.sign.ResponseSignInData
import com.dnd_8th_4_android.wery.presentation.di.HttpClient
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(private val authService: AuthService) :
    AuthRemoteDataSource {

    override suspend fun loginUser(body: RequestSignInData): Result<ResponseSignInData> {
        val response = authService.loginUser(body = body)
        if (response.isSuccessful) {
            response.body()?.let { return Result.success(it) }
        }
        return Result.failure(IllegalStateException(response.message()))
    }
}