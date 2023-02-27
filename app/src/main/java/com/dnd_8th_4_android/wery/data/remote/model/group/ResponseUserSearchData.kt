package com.dnd_8th_4_android.wery.data.remote.model.group

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseUserSearchData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val userSearchInfoList: MutableList<UserSearchInfoList>,
    ) {
        data class UserSearchInfoList(
            val userId: Int,
            val userNickName: String,
            val profileImageUrl: String,
        )
    }
}