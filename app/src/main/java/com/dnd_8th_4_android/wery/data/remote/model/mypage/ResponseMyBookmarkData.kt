package com.dnd_8th_4_android.wery.data.remote.model.mypage

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseMyBookmarkData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val content: MutableList<Content>,
    ) {
        data class Content(
            val contentId: Int,
            val userId: Int,
            val groupId: Int,
            val groupName: String,
            val groupImage: String,
            val content: String,
            val createAt: String,
            val views: Int,
            val comments: Int,
            val imageSize: Int,
            val images: MutableList<Images>,
        ) {
            data class Images(
                val imageUrl: String,
                val contentId: Int,
            )
        }
    }
}