package com.dnd_8th_4_android.wery.data.remote.model.detail

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class ResponsePostDetailCommentData(
    val data : Data
): BaseResponse() {
    data class Data(
        val content: MutableList<Content>
    ) {
        data class Content(
        val friendImage: String?,
            @SerializedName("username") val name: String,
        val sticker: Int?,
            @SerializedName("commentNote") val comment: String,
            @SerializedName("createdAt") val time: String,
//        val likesExists: Boolean
        )
    }
}