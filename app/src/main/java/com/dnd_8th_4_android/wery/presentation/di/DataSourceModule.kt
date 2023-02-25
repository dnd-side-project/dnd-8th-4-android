package com.dnd_8th_4_android.wery.presentation.di

import com.dnd_8th_4_android.wery.data.api.*
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

    @Provides
    @Singleton
    fun provideDetailDataSource(detailService: DetailService): DetailDataSource {
        return DetailDataSourceImpl(detailService)
    }

    @Provides
    @Singleton
    fun providePopupBottomDataSource(popupBottomService: PopupBottomService): PopupBottomDataSource {
        return PopupBottomDataSourceImpl(popupBottomService)
    }
}