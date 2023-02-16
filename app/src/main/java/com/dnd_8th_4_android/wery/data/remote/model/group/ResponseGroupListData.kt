package com.dnd_8th_4_android.wery.data.remote.model.group

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseGroupListData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val image: Int,
        val name: String,
        val introduce: String,
        val time: String,
        val number: Int,
        val isSelected: Boolean
    )
}
