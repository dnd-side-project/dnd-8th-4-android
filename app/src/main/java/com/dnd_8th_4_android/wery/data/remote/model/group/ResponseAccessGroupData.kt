package com.dnd_8th_4_android.wery.data.remote.model.group

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseAccessGroupData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val id: Int,
        val day: Int,
        val content: String,
        val startDay: String,
        val endDay: String,
        var isSelected: Boolean,
    )
}
