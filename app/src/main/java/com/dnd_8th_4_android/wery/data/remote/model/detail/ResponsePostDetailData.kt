package com.dnd_8th_4_android.wery.data.remote.model.detail

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class ResponsePostDetailData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val userName: String,
        val location: String,
        val views: Int,
        val userId: Int,
        @SerializedName("collect") val imageList: MutableList<Images>,
    ) {
        data class Images(
            val imageUrl: String,
        )
    }
}