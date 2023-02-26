package com.dnd_8th_4_android.wery.data.remote.model.group

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseSetBookmarkData(
    val data: Data,
): BaseResponse() {
    data class Data(
        val groupStarYn: String
    )
}