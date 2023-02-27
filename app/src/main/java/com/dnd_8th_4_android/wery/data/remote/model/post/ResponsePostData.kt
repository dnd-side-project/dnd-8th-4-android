package com.dnd_8th_4_android.wery.data.remote.model.post

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponsePostData(
    val data: ResultPost
) : BaseResponse() {
    data class ResultPost(
        val collect: List<Collect>,
        val content: String,
        val groupName: String,
        val id: Int,
        val latitude: Double?,
        val location: String?,
        val longitude: Double?,
    ) {
        data class Collect(
            val imageUrl: String
        )
    }
}