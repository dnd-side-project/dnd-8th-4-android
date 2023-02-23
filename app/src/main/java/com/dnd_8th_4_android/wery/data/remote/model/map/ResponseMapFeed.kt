package com.dnd_8th_4_android.wery.data.remote.model.map

data class ResponseMapFeed(
    val title: String,
    val content: String,
    val groupName: String,
    val groupImgUrl: String,
    val groupPhotoImgUrl: String,
    val groupPhotoCnt: Int,
    val createDate: String
)