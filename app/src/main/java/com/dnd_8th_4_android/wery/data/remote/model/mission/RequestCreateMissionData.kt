package com.dnd_8th_4_android.wery.data.remote.model.mission

data class RequestCreateMissionData(
    val missionName: String,
    val missionNote: String? = null,
    val groupId: Int,
    val existPeriod: Boolean,
    val missionStartDate: String? = null,
    val missionEndDate: String? = null,
    val missionLocationName: String,
    val missionLocationAddress: String,
    val latitude: Double,
    val longitude: Double,
    val missionColor: Int
)