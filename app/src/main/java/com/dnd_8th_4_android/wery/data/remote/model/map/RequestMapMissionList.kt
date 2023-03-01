package com.dnd_8th_4_android.wery.data.remote.model.map

data class RequestMapMissionList(
    val startLatitude: Double,
    val startLongitude: Double,
    val endLatitude: Double,
    val endLongitude: Double
)