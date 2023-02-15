package com.dnd_8th_4_android.wery.data.remote.model.sign

import com.google.gson.annotations.SerializedName

data class ResponseCommentData(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: Data
) {
    data class Data(
        @SerializedName("message") val message: String
    )
}