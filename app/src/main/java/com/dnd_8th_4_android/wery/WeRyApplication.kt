package com.dnd_8th_4_android.wery

import android.app.Application
import android.util.TypedValue
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeRyApplication:Application() {

    companion object {
        val KAKAO_URL = "https://dapi.kakao.com/"
    }

    override fun onCreate() {
        super.onCreate()
    }
}