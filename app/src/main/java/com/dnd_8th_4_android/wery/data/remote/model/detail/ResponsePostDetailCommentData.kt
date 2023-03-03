package com.dnd_8th_4_android.wery.data.remote.model.detail

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class ResponsePostDetailCommentData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val content: MutableList<Content>,
    ) {
        data class Content(
            val userId: Int,
            val profileImageUrl: String,
            @SerializedName("username") val name: String,
            @SerializedName("stickerImageUrl") val sticker: String?,
            @SerializedName("commentNote") val comment: String,
            @SerializedName("createdAt") val time: String,
//        val likesExists: Boolean
        )
    }
}