package com.dnd_8th_4_android.wery.presentation.di

import com.dnd_8th_4_android.wery.data.remote.datasource.*
import com.dnd_8th_4_android.wery.data.repository.*
import com.dnd_8th_4_android.wery.domain.repository.*

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

    @Provides
    @Singleton
    fun provideDetailRepository(detailDataSource: DetailDataSource): DetailRepository {
        return DetailRepositoryImpl(detailDataSource)
    }

    @Provides
    @Singleton
    fun providePopupBottomRepository(popupBottomDataSource: PopupBottomDataSource): PopupBottomRepository {
        return PopupBottomRepositoryImpl(popupBottomDataSource)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(searchDataSource: SearchDataSource): SearchRepository {
        return SearchRepositoryImpl(searchDataSource)
    }

    @Provides
    @Singleton
    fun provideGroupRepository(groupDataSource: GroupDataSource): GroupRepository {
        return GroupRepositoryImpl(groupDataSource)
    }
}