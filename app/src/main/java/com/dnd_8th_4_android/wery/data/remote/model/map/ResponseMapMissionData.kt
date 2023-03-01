package com.dnd_8th_4_android.wery.data.remote.model.map

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

class ResponseMapMissionData(
    val data:ResultMapMissionData
) : BaseResponse() {
    data class ResultMapMissionData(
        val createUserId: Int,
        val createUserName: String,
        val createUserProfileImageUrl: String,
        val existPeriod: Boolean,
        val groupId: Int,
        val groupImageUrl: String,
        val groupName: String,
        val latitude: Double,
        val longitude: Double,
        val missionColor: Int,
        val missionDday: Int,
        val missionEndDate: String,
        val missionId: Int,
        val missionLocationAddress: Any,
        val missionLocationName: String,
        val missionName: String,
        val missionNote: Any,
        val missionStartDate: String,
        val missionStatus: String,
    )

}