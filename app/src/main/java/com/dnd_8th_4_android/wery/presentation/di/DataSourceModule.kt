package com.dnd_8th_4_android.wery.presentation.di

import com.dnd_8th_4_android.wery.data.api.AuthService
import com.dnd_8th_4_android.wery.data.api.PlaceService
import com.dnd_8th_4_android.wery.data.api.SignUpService
import com.dnd_8th_4_android.wery.data.remote.datasource.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @OtherHttpClient
    @Provides
    @Singleton
    fun providePlaceDataSource(@OtherHttpClient placeService: PlaceService): PlaceRemoteDataSource {
        return PlaceRemoteDataSourceImpl(placeService)
    }

    @HttpClient
    @Provides
    @Singleton
    fun provideAuthDataSource(@HttpClient authService: AuthService): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(authService)
    }

    @HttpClient
    @Provides
    @Singleton
    fun provideSignUpDataSource(@HttpClient signUpService: SignUpService): SignUpDataSource {
        return SignUpDataSourceImpl(signUpService)
    }
}