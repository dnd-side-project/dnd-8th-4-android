package com.dnd_8th_4_android.wery.data.remote.model.mission

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName


data class ResponseMyMissionList(
    val data: List<ResponseMyMissionCard>
) : BaseResponse()

data class ResponseMyMissionCard(
    val missionId: Long? = 0,
    @SerializedName("missionName") val missionTitle: String,
    val groupName: String,
    val groupImageUrl: String,
    val missionStartDate: String,
    val missionEndDate: String,
)