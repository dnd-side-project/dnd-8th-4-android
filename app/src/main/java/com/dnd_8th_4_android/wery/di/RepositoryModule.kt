package com.dnd_8th_4_android.wery.di

import com.dnd_8th_4_android.wery.data.repository.PlaceRepositoryImpl
import com.dnd_8th_4_android.wery.domain.repository.PlaceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsDiariesRepository(placeRepositoryImpl: PlaceRepositoryImpl): PlaceRepository
}