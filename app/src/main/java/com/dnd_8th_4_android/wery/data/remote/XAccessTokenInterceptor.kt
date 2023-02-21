package com.dnd_8th_4_android.wery.data.remote

import android.content.SharedPreferences
import android.util.Log
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource.Companion.ACCESS_TOKEN
import com.dnd_8th_4_android.wery.presentation.di.HttpClient
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class XAccessTokenInterceptor @Inject constructor(@HttpClient private val sharedPreferences: SharedPreferences) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val atk: String? = sharedPreferences.getString(ACCESS_TOKEN, null)
        if (atk != null) {
            builder.addHeader("Authorization", "Bearer $atk")
        }
        return chain.proceed(builder.build())
    }
}