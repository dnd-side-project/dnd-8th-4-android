package com.dnd_8th_4_android.wery.data.remote.model.search

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponsePostSearchData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val content: MutableList<Content>,
    ) {
        data class Content(
            val contentId: Int,
            val groupId: Int,
            val content: String,
            val groupImage: String,
            val groupName: String,
            val createAt: String,
            val contentImageListSize: Int,
            val contentImageList: ArrayList<Images>,
        ) {
            data class Images(
                val imageUrl: String,
            )
        }
    }
}