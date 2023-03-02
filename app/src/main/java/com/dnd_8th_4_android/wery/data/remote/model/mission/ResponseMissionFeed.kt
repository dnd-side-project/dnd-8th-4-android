package com.dnd_8th_4_android.wery.data.remote.model.mission

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

class ResponseMissionFeed(
    val data: ResultStickerState
) : BaseResponse() {
    data class ResultStickerState(
        val isGetNewSticker: Boolean,
        val currMainLevel: Int,
        val getNewStickerGroupId: Int? = -1
    )
}