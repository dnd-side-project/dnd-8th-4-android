package com.dnd_8th_4_android.wery.presentation.di

import com.dnd_8th_4_android.wery.data.api.AuthService
import com.dnd_8th_4_android.wery.data.api.PlaceService
import com.dnd_8th_4_android.wery.data.api.SignUpService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @OtherHttpClient
    @Provides
    @Singleton
    fun providePlaceService(@OtherHttpClient retrofit: Retrofit): PlaceService {
        return retrofit.create(PlaceService::class.java)
    }

    @HttpClient
    @Provides
    @Singleton
    fun provideAuthService(@HttpClient retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @HttpClient
    @Provides
    @Singleton
    fun provideSignUpService(@HttpClient retrofit: Retrofit): SignUpService {
        return retrofit.create(SignUpService::class.java)
    }
}