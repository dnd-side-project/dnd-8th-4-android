package com.dnd_8th_4_android.wery.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PostLocalDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPreferences =
        context.getSharedPreferences(WERY_APP, Context.MODE_PRIVATE)

    var uploadFromMapState: Boolean
        set(value) = sharedPreferences.edit().putBoolean(MAP_UPLOAD_STATE, value).apply()
        get() = sharedPreferences.getBoolean(MAP_UPLOAD_STATE, false)

    var mapUploadPlace: String?
        set(value) = sharedPreferences.edit().putString(MAP_UPLOAD_PLACE, value).apply()
        get() = sharedPreferences.getString(MAP_UPLOAD_PLACE,"")

    var mapLatitude: Float
        set(value) = sharedPreferences.edit().putFloat(MAP_UPLOAD_LATITUDE, value).apply()
        get() = sharedPreferences.getFloat(MAP_UPLOAD_LATITUDE, 0.0f)

    var mapLongitude: Float
        set(value) = sharedPreferences.edit().putFloat(MAP_UPLOAD_LONGITUDE, value).apply()
        get() = sharedPreferences.getFloat(MAP_UPLOAD_LONGITUDE, 0.0f)

    companion object {
        const val WERY_APP = "WERY_APP"
        const val MAP_UPLOAD_STATE = "MAP_UPLOAD_STATE"
        const val MAP_UPLOAD_PLACE = "MAP_UPLOAD_PLACE"
        const val MAP_UPLOAD_LATITUDE = "MAP_UPLOAD_LATITUDE"
        const val MAP_UPLOAD_LONGITUDE = "MAP_UPLOAD_LONGITUDE"
    }
}