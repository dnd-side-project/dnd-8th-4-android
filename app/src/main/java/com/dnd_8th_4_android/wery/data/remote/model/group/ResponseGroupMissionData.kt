package com.dnd_8th_4_android.wery.data.remote.model.group

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class ResponseGroupMissionData(
    val data: MutableList<Data>,
) : BaseResponse() {
    data class Data(
        val missionId: Int,
        val missionColor: Int,
        @SerializedName("missionName") val missionTitle: String,
        @SerializedName("missionNote") val missionContent: String,
        val groupName: String,
        val existPeriod: Boolean,
        val missionStartDate: String,
        val missionEndDate: String,
        val missionStatus: String,
        val missionDday: Int,
        val userAssignMissionInfo: UserAssignMissionInfo,
    ) {
        data class UserAssignMissionInfo(
            val locationCheck: Boolean,
            val contentCheck: Boolean,
            val isComplete: Boolean,
        )
    }
}