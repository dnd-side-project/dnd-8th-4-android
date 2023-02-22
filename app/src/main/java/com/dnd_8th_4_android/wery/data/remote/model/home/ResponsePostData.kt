package com.dnd_8th_4_android.wery.data.remote.model.home

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class ResponsePostData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val content: MutableList<Content>
    ) {
        data class Content(
            val id: Int,
            val userId: Int,
            @SerializedName("userName") val name: String,
            val groupName: String,
            val content: String,
            @SerializedName("createAt") val time: String,
            @SerializedName("views") val hit: String,
            val comments: Int,
            val emotions: Int,
            @SerializedName("emotionStatus") var isSelectedEmotion: Int,
            @SerializedName("emotionResponseDtos") var emotion: MutableList<Int>,
            @SerializedName("images") val contentImage: ArrayList<Images>,
//            val contentLink: String,
//            val image: Int,
//            val location: String,
        ) {
            data class Images (
                val imageUrl: String
            )
        }
    }
}
