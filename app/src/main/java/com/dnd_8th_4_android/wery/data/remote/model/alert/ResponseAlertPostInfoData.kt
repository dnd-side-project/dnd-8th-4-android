package com.dnd_8th_4_android.wery.data.remote.model.alert

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseAlertPostInfoData(
    val data: Data?
) : BaseResponse() {
    data class Data(
        val content: String?,
        val profileImageUrl: String,
        val groupName: String,
    )
}