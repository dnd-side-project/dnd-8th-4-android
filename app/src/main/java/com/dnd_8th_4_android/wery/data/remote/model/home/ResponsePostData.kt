package com.dnd_8th_4_android.wery.data.remote.model.home

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class ResponsePostData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        var content: MutableList<Content>,
        val totalPages: Int,
        val last: Boolean,
    ) {
        data class Content(
            val id: Int,
            val userId: Int,
            @SerializedName("userName") val name: String,
            @SerializedName("profileImageUrl") val image: String,
            val groupName: String,
            val content: String,
            val location: String?,
            val createAt: String,
            @SerializedName("views") val hit: String,
            val comments: Int,
            val emotions: Int,
            var emotionStatus: Int,
            @SerializedName("emotionResponseDtos") var emotion: MutableList<EmotionDtos>,
            @SerializedName("images") val contentImage: ArrayList<Images>,
            val bookmarkAddStatus: Boolean,
        ) {
            data class Images(
                val imageUrl: String,
            ): java.io.Serializable

            data class EmotionDtos(
                val emotionStatus: Int,
            )
        }

    }
}
