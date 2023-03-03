package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.MyPageDataSource
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyInfo
import com.dnd_8th_4_android.wery.domain.repository.MyPageRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(private val myPageDataSource: MyPageDataSource): MyPageRepository {

    override suspend fun getMyInfo(): ResponseMyInfo {
        return myPageDataSource.getMyInfo()
    }

    override suspend fun modifyProfile(
        data: HashMap<String, RequestBody>,
        image: MultipartBody.Part?,
    ): BaseResponse {
        return myPageDataSource.modifyProfile(data, image)
    }

    override suspend fun getMyBookmarkList(page: Int): ResponseMyBookmarkData {
        return myPageDataSource.getMyBookmarkList(page)
    }
}