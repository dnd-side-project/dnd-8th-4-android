package com.dnd_8th_4_android.wery.data.remote.model.home

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseGroupData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val id: Int,
        val image: Int,
        val name: String,
    )
}
