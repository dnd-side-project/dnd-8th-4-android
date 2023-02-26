package com.dnd_8th_4_android.wery.data.remote.model.mission

data class ResponseSticker(
    val isStickerLocked: Boolean,
    val stickerName: String,
    val stickerImgUrl:String,
    val stickerLevel: Int,
    val stickerDue: String
)