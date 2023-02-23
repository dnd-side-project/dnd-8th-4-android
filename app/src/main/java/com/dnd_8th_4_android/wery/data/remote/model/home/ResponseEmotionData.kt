package com.dnd_8th_4_android.wery.data.remote.model.home

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseEmotionData(
    val data: Data?,
) : BaseResponse() {
    data class Data(
        val emotionStatus: Int,
    )
}
