package com.dnd_8th_4_android.wery.data.remote.model.mission

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseMissionCertifyData(
    val data: Data
): BaseResponse() {
    data class Data(
        val locationCheck: Boolean
    )
}