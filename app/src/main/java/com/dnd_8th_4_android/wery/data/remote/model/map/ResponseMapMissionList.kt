package com.dnd_8th_4_android.wery.data.remote.model.map

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse


data class ResponseMapMissionList(
    val data: List<ResultMapMission>
) : BaseResponse() {
    data class ResultMapMission(
        val x: Double,
        val y: Double,
    )
}

