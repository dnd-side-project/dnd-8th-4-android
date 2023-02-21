package com.dnd_8th_4_android.wery.presentation.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherHttpClient