package com.dnd_8th_4_android.wery.data.remote.model.mission

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseMissionDetailData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val missionId: Int,
        val missionName: String,
        val missionNote: String,
        val createUserId: Int,
        val groupId: Int,
        val groupName: String,
        val groupImageUrl: String,
        val existPeriod: Boolean,
        val missionStartDate: String,
        val missionEndDate: String,
//        val missionStatus: String,
        val missionLocationName: String,
        val latitude: Double,
        val longitude: Double,
//        val userAssignMissionInfoList: UserAssignMissionInfoList
    ) {
        data class UserAssignMissionInfoList(
            val locationCheck: Boolean,
            val contentCheck: Boolean,
        )
    }
}