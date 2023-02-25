package com.dnd_8th_4_android.wery.data.remote.model.mission

import com.google.gson.annotations.SerializedName

data class ResponseMissionCard(
    val missionId: Long,
    val missionColor: Int,
    @SerializedName("missionName") val missionTitle: String,
    @SerializedName("missionNote") val missionContent: String,
    val groupName: String,
    val groupImageUrl: String,
    val missionStartDate: String,
    val missionEndDate: String,
    val missionStatus: String,
    val missionDday: Int
)