package com.dnd_8th_4_android.wery.data.remote.model.detail

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponsePostDetailCommentNoImageData(
    val data : Data
): BaseResponse() {
    data class Data(
        val friendImage: Int,
        val name: String,
        val comment: String,
        val time: String
    )
}