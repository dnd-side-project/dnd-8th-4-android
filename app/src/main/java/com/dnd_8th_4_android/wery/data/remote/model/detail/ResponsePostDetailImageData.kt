package com.dnd_8th_4_android.wery.data.remote.model.detail

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponsePostDetailImageData(
    val data : Data
): BaseResponse() {
    data class Data(
        val imageList: ArrayList<Int>
    )
}