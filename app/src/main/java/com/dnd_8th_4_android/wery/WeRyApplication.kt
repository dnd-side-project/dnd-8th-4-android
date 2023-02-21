package com.dnd_8th_4_android.wery

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeRyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
    }
}