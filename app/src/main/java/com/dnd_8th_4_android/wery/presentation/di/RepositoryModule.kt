package com.dnd_8th_4_android.wery.presentation.di

import com.dnd_8th_4_android.wery.data.remote.datasource.AuthRemoteDataSource
import com.dnd_8th_4_android.wery.data.remote.datasource.PlaceRemoteDataSource
import com.dnd_8th_4_android.wery.data.remote.datasource.SignUpDataSource
import com.dnd_8th_4_android.wery.data.repository.AuthRepositoryImpl
import com.dnd_8th_4_android.wery.data.repository.PlaceRepositoryImpl
import com.dnd_8th_4_android.wery.data.repository.SignUpRepositoryImpl
import com.dnd_8th_4_android.wery.domain.repository.AuthRepository

import com.dnd_8th_4_android.wery.domain.repository.PlaceRepository
import com.dnd_8th_4_android.wery.domain.repository.SignUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @OtherHttpClient
    @Provides
    @Singleton
    fun bindsPlaceRepository(@OtherHttpClient placeRemoteDataSource: PlaceRemoteDataSource): PlaceRepository {
        return PlaceRepositoryImpl(placeRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideSignInRepository(authRemoteDataSource: AuthRemoteDataSource): AuthRepository {
        return AuthRepositoryImpl(authRemoteDataSource)
    }


    @Provides
    @Singleton
    fun provideSignUpRepository(signUpDataSource: SignUpDataSource): SignUpRepository {
        return SignUpRepositoryImpl(signUpDataSource)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(homeDataSource: HomeDataSource): HomeRepository {
        return HomeRepositoryImpl(homeDataSource)
    }
}