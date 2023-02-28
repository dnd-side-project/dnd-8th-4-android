package com.dnd_8th_4_android.wery.data.remote.model.mission

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class ResponseSticker(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val currMissionInfo: CurrMissionInfo,
        val acquisitionStickerInfo: MutableList<AcquisitionStickerInfo>,
    ) {
        data class CurrMissionInfo(
            val subLevel: Int,
            val mainLevel: Int,
            val remainToUpMainLevel: Int,
        )

        data class AcquisitionStickerInfo(
            val stickerId: Int,
            val stickerName: String,
            val stickerLevel: Int,
            @SerializedName("stickerUrl") val stickerImgUrl: String,
            @SerializedName("isAcquisitionSticker") val isStickerLocked: Boolean,
        )
    }
}