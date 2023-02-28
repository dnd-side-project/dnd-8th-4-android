package com.dnd_8th_4_android.wery.data.remote.model.mission

data class RequestMissionCertifyData(
    val missionId: Int,
    val groupId: Int,
    val currLatitude: Double,
    val currLongitude: Double,
)