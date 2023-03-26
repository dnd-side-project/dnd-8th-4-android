package com.dnd_8th_4_android.wery.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AuthLocalDataSource @Inject constructor(
    @ApplicationContext context: Context
) {
    private val sharedPreferences =
        context.getSharedPreferences(WERY_APP, Context.MODE_PRIVATE)

    var accessToken: String?
        set(value) = sharedPreferences.edit().putString(ACCESS_TOKEN, value).apply()
        get() = sharedPreferences.getString(ACCESS_TOKEN, null)

    var isAutoLogin: Boolean
        set(value) = sharedPreferences.edit().putBoolean(IS_AUTO_LOGIN, value).apply()
        get() = sharedPreferences.getBoolean(IS_AUTO_LOGIN, false)

    var hasOnboardDone: Boolean
        set(value) = sharedPreferences.edit().putBoolean(HAS_ONBOARD_DONE, value).apply()
        get() = sharedPreferences.getBoolean(HAS_ONBOARD_DONE, false)

    var userId: Int
        set(value) = sharedPreferences.edit().putInt(USER_ID, value).apply()
        get() = sharedPreferences.getInt(USER_ID, 0)

    companion object {
        const val WERY_APP = "WERY_APP"
        const val ACCESS_TOKEN = "accessToken"
        const val IS_AUTO_LOGIN = "isAutoLogin"
        const val HAS_ONBOARD_DONE = "hasOnboardDone"
        const val USER_ID = "userId"
    }
}



