package com.dnd_8th_4_android.wery.data.remote.model.map

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseMapFeedData(
    val data: List<ResultMapFeedData>,
) : BaseResponse() {
    data class ResultMapFeedData(
        val content: String,
        val contentId: Int,
        val contentImageSize: Int,
        val contentImageUrl: String,
        val createAt: String,
        val groupId: Int,
        val groupName: String,
        val groupImage: String,
        val location: String
    )
}
