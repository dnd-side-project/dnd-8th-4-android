package com.dnd_8th_4_android.wery.data.remote.model.mypage

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseMyInfo(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val id: Int,
        val email: String,
        val name: String,
        val nickName: String,
        val phoneNumber: String,
        val profileImageUrl: String,
    )
}
