package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.MyPageService
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyInfo
import javax.inject.Inject

class MyPageDataSourceImpl @Inject constructor(private val myPageService: MyPageService): MyPageDataSource {

    override suspend fun getMyInfo(): ResponseMyInfo {
        return myPageService.getMyInfo()
    }
}