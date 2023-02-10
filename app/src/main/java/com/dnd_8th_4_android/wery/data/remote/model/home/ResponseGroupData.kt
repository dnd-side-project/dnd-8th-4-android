package com.dnd_8th_4_android.wery.data.remote.model.home

import com.google.gson.annotations.SerializedName

data class ResponseGroupData(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: Data,
) {
    data class Data(
        val name: String,
    )
}
