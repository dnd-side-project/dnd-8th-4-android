package com.dnd_8th_4_android.wery.di

import com.dnd_8th_4_android.wery.data.api.PlaceService
import com.dnd_8th_4_android.wery.data.remote.model.datasource.PlaceRemoteDataSource
import com.dnd_8th_4_android.wery.data.remote.model.datasource.PlaceRemoteDataSourceImpl
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
    fun provideUserDataSource(
        placeService: PlaceService
    ): PlaceRemoteDataSource {
        return PlaceRemoteDataSourceImpl(placeService)
    }
}