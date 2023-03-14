package com.dnd_8th_4_android.wery.presentation.ui.mypage.viewmodel

import androidx.lifecycle.ViewModel
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageConfigurationViewModel @Inject constructor(private val authLocalDataSource: AuthLocalDataSource) :
    ViewModel() {

    fun removeAutoLoginState() {
        authLocalDataSource.isAutoLogin = false
    }
}