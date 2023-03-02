package com.dnd_8th_4_android.wery.data.remote.model.mission

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseStickerDetail(
    val data: Data
): BaseResponse() {
    data class Data(
        val stickerGroupId: Int,
        val stickerGroupName: String,
        val stickerGroupLevel: Int,
        val stickerGroupThumbnailUrl: String,
        val stickerInfoList: MutableList<StickerInfoList>
    ) {
        data class StickerInfoList(
            val stickerImageUrl: String,
        )
    }
}