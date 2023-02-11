package com.dnd_8th_4_android.wery.data.remote.model.home

data class ResponsePostData(
    val success: Boolean,
    val data: Data,
) {
    data class Data(
//        val image: Int,
        val name: String,
        val groupName: String,
        val content: String,
        val contentImage: List<Int>,
//        val emotion: List<Int>,
//        val comment: List<String>,
        val time: String,
        val hit: String,
    )
}
