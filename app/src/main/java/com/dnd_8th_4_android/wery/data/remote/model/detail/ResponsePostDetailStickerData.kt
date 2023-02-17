package com.dnd_8th_4_android.wery.data.remote.model.detail

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponsePostDetailStickerData(
    val data : Data
): BaseResponse() {
    data class Data(
        val stickerList: List<Int>
    )
}