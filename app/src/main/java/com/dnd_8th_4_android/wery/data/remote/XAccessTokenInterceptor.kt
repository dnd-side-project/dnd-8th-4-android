package com.dnd_8th_4_android.wery.data.remote

import android.content.Context
import com.dnd_8th_4_android.wery.WeRyApplication
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource.Companion.ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val sharedPreferences = WeRyApplication().getAppContext()
            ?.getSharedPreferences(AuthLocalDataSource.WERY_APP, Context.MODE_PRIVATE)
        val builder: Request.Builder = chain.request().newBuilder()
        val atk: String? = sharedPreferences?.getString(ACCESS_TOKEN, null)
        if (atk != null) {
            builder.addHeader("Authorization", "Bearer $atk")
        }
        return chain.proceed(builder.build())
    }
}