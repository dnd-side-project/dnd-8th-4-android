package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.AuthRemoteDataSource
import com.dnd_8th_4_android.wery.data.remote.model.sign.RequestSignInData
import com.dnd_8th_4_android.wery.data.remote.model.sign.ResponseSignInData
import com.dnd_8th_4_android.wery.domain.repository.AuthRepository
import com.dnd_8th_4_android.wery.presentation.di.HttpClient
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authRemoteDataSource: AuthRemoteDataSource) :
    AuthRepository {
    override suspend fun loginUser(body: RequestSignInData): Result<ResponseSignInData> {
        return authRemoteDataSource.loginUser(body = body)
    }
}