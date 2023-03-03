package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.MyPageService
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MyPageDataSourceImpl @Inject constructor(private val myPageService: MyPageService): MyPageDataSource {

    override suspend fun getMyInfo(): ResponseMyInfo {
        return myPageService.getMyInfo()
    }

    override suspend fun modifyProfile(
        data: HashMap<String, RequestBody>,
        image: MultipartBody.Part?,
    ): BaseResponse {
        return myPageService.modifyProfile(data, image)
    }

    override suspend fun getMyBookmarkList(page: Int): ResponseMyBookmarkData {
        return myPageService.getMyBookmarkList(page)
    }
}