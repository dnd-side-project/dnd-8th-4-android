package com.dnd_8th_4_android.wery

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeRyApplication:Application() {
    private var mContext: Context? = null

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }

    fun getAppContext(): Context? {
        return mContext
    }
}