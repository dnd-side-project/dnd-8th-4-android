package com.dnd_8th_4_android.wery.data.remote.model.home

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponsePostData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val id: Int,
        val image: Int,
        val name: String,
        val location: String,
        val groupName: String,
        val content: String,
        val contentImage: List<Int>,
        var emotion: MutableList<Int>,
        val comment: List<String>,
        val time: String,
        val hit: String,
        var isSelectedEmotion: Int,
    )
}
