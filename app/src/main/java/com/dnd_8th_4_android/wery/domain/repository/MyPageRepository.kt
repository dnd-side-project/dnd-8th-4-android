package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyInfo

interface MyPageRepository {

    suspend fun getMyInfo(): ResponseMyInfo
}