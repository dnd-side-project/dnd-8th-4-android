package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyInfo

interface MyPageDataSource {

    suspend fun getMyInfo(): ResponseMyInfo
}