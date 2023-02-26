package com.dnd_8th_4_android.wery.data.remote.model.group

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseBookmarkData(
    val data: MutableList<Data>,
) : BaseResponse() {
    data class Data(
        val groupId: Int,
//        val groupImage: String,
        val groupName: String,
    )
}