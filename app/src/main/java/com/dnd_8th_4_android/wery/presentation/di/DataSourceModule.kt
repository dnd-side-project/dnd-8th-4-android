package com.dnd_8th_4_android.wery.presentation.di

import com.dnd_8th_4_android.wery.data.api.AuthService
import com.dnd_8th_4_android.wery.data.api.HomeService
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

    @Provides
    @Singleton
    fun provideSignUpDataSource(signUpService: SignUpService): SignUpDataSource {
        return SignUpDataSourceImpl(signUpService)
    }

    @Provides
    @Singleton
    fun provideHomeDataSource(homeService: HomeService): HomeDataSource {
        return HomeDataSourceImpl(homeService)
    }
}