package com.dnd_8th_4_android.wery.data.remote.model.detail

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponsePostDetailEmotionData(
    val data : Data
): BaseResponse() {
    data class Data(
        val imageEmotion: Pair<Int, Int>
    )
}