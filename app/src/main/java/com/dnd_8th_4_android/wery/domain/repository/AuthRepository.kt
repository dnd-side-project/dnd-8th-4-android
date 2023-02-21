package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.sign.RequestSignInData
import com.dnd_8th_4_android.wery.data.remote.model.sign.ResponseSignInData

interface AuthRepository {

    suspend fun loginUser(body: RequestSignInData): Result<ResponseSignInData>
}
