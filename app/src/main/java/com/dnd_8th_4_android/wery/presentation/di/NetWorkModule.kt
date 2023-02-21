package com.dnd_8th_4_android.wery.presentation.di


import android.content.SharedPreferences
import com.dnd_8th_4_android.wery.BuildConfig
import com.dnd_8th_4_android.wery.WeRyApplication.Companion.KAKAO_URL
import com.dnd_8th_4_android.wery.data.remote.XAccessTokenInterceptor
import com.dnd_8th_4_android.wery.presentation.di.NetWorkModule_ProvideHttpClientFactory.provideHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Provides
    @Singleton
    fun provideXAccessTokenInterceptor(sharedPreferences: SharedPreferences): Interceptor {
        return XAccessTokenInterceptor(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideHttpClient(xAccessTokenInterceptor: XAccessTokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(xAccessTokenInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @OtherHttpClient
    @Provides
    @Singleton
    fun provideOtherHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @OtherHttpClient
    @Singleton
    @Provides
    fun provideOtherRetrofitInstance(
        @OtherHttpClient okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(KAKAO_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory).build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}