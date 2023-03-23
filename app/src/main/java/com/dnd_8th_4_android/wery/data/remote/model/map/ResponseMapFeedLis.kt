package com.dnd_8th_4_android.wery.data.remote.model.map

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseMapFeedLis(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val content: List<Content>,
        val totalPages: Int
    ) {
        data class Content(
            val contentImageUrl: String,
            val counts: Int,
            val groupId: Int,
            val id: Int,
            val latitude: Double,
            val location: String,
            val longitude: Double,
            val userId: Int
        )
    }
}
