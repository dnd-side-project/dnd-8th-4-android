package com.dnd_8th_4_android.wery.presentation.di

import com.dnd_8th_4_android.wery.data.api.AuthService
import com.dnd_8th_4_android.wery.data.api.PlaceService
import com.dnd_8th_4_android.wery.data.remote.datasource.AuthRemoteDataSource
import com.dnd_8th_4_android.wery.data.remote.datasource.AuthRemoteDataSourceImpl
import com.dnd_8th_4_android.wery.data.remote.datasource.PlaceRemoteDataSource
import com.dnd_8th_4_android.wery.data.remote.datasource.PlaceRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providePlaceDataSource(placeService: PlaceService): PlaceRemoteDataSource {
        return PlaceRemoteDataSourceImpl(placeService)
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(authService: AuthService): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(authService)
    }
}