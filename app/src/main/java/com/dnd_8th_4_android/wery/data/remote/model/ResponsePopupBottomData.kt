package com.dnd_8th_4_android.wery.data.remote.model

data class ResponsePopupBottomData(
    val data: Data?,
) : BaseResponse() {
    data class Data(
        val id: Int,
    )
}