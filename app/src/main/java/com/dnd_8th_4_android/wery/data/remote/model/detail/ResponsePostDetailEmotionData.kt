package com.dnd_8th_4_android.wery.data.remote.model.detail

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class ResponsePostDetailEmotionData(
    val data: MutableList<Data>,
) : BaseResponse() {
    data class Data(
        var userId: Int,
        @SerializedName("profileImageUrl") var userImage: String?,
        var emotionStatus: Int,
    )
}