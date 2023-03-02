package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.MyPageDataSource
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyInfo
import com.dnd_8th_4_android.wery.domain.repository.MyPageRepository
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(private val myPageDataSource: MyPageDataSource): MyPageRepository {

    override suspend fun getMyInfo(): ResponseMyInfo {
        return myPageDataSource.getMyInfo()
    }
}