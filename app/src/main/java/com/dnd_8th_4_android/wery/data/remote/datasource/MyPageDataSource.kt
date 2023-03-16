package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Query

interface MyPageDataSource {

    suspend fun getMyInfo(): ResponseMyInfo

    suspend fun modifyProfile(
        data: HashMap<String, RequestBody>,
        image: MultipartBody.Part?,
    ): BaseResponse

    suspend fun getMyBookmarkList(
        page: Int,
    ): ResponseMyBookmarkData

    suspend fun deleteAccount(): BaseResponse
}