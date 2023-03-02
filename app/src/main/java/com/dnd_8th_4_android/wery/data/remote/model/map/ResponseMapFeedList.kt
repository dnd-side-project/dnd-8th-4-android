package com.dnd_8th_4_android.wery.data.remote.model.map

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseMapFeedList(
    val data: List<ResultMapFeedData>,
) : BaseResponse() {
    data class ResultMapFeedData(
        val collect: List<Collect>,
        val groupId: Int,
        val id: Int,
        val latitude: Double,
        val longitude: Double,
        val userId: Int
    )
}

data class Collect(
    val contentId: Int,
    val id: Int,
    val imageName: String,
    val imageUrl: String
)