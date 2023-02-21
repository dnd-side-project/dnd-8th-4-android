package com.dnd_8th_4_android.wery.presentation.di

import com.dnd_8th_4_android.wery.data.remote.datasource.SignUpDataSource
import com.dnd_8th_4_android.wery.data.repository.PlaceRepositoryImpl
import com.dnd_8th_4_android.wery.data.repository.SignUpRepositoryImpl
import com.dnd_8th_4_android.wery.domain.repository.PlaceRepository
import com.dnd_8th_4_android.wery.domain.repository.SignUpRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSignUpRepository(signUpDataSource: SignUpDataSource): SignUpRepository {
        return SignUpRepositoryImpl(signUpDataSource)
    }
}