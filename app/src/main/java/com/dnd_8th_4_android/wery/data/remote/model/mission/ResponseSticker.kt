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
            val subLevel: Float,
            val mainLevel: Int,
            val progressBarRange: Int,
        )

        data class AcquisitionStickerInfo(
            @SerializedName("stickerGroupId") val stickerId: Int,
            @SerializedName("stickerGroupName") val stickerName: String,
            @SerializedName("stickerGroupLevel") val stickerLevel: Int,
            @SerializedName("stickerGroupThumbnailUrl") val stickerImgUrl: String,
            @SerializedName("isAcquisitionStickerGroup") val isStickerLocked: Boolean,
        )
    }
}