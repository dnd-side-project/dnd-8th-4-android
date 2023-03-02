package com.dnd_8th_4_android.wery.data.remote.model.map

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseMapFeedData(
    val data: List<ResultMapFeedData>,
) : BaseResponse() {
    data class ResultMapFeedData(
        val content: String,
        val contentId: Int,
        val contentImageList: List<ContentImage>,
        val contentImageListSize: Int,
        val createAt: String,
        val groupId: Int,
        val groupName: String,
        val location: String
    )
}

data class ContentImage(
    val contentId: Int,
    val id: Int,
    val imageName: String,
    val imageUrl: String
)