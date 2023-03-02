package com.dnd_8th_4_android.wery.data.remote.model.detail

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponsePostDetailStickerData(
    val data : MutableList<Data>
): BaseResponse() {
    data class Data(
        val stickerInfoList: MutableList<StickerInfoList>
    ) {
        data class StickerInfoList(
            val stickerId: Int,
            val stickerImageUrl: String,
        )
    }
}