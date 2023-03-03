package com.dnd_8th_4_android.wery.data.remote.model.map

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseMapFeedData(
    val data: List<ResultMapFeedData>,
) : BaseResponse() {
    data class ResultMapFeedData(
        val contentId: Int,
        val groupId: Int,
        val location: String,
        val groupImage: String,
        val groupName: String,
        val createAt: String,
        val contentImageSize: Int,
        val contentImageUrl: String,
        val content: String,
    )
}
