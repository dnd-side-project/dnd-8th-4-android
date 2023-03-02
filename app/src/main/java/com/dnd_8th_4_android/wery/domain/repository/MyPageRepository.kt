package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface MyPageRepository {

    suspend fun getMyInfo(): ResponseMyInfo

    suspend fun modifyProfile(
        data: HashMap<String, RequestBody>,
        image: MultipartBody.Part?
    ): BaseResponse
}