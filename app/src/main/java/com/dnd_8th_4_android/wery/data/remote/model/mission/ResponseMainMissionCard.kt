package com.dnd_8th_4_android.wery.data.remote.model.mission

import com.google.gson.annotations.SerializedName


data class ResponseMainMissionCard(
    val data: List<ResultMissionCard>
) {
    data class ResultMissionCard(
        val missionId: Long,
        val missionColor: Int,
        val groupId:Int,
        @SerializedName("missionName") val missionTitle: String,
        @SerializedName("missionNote") val missionContent: String,
        val groupName: String,
        val groupImageUrl: String,
        var missionStartDate: String,
        val missionEndDate: String,
        val missionStatus: String,
        val missionDday: Int
    )
}

