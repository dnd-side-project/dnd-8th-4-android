package com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authLocalDataSource: AuthLocalDataSource) :
    ViewModel() {
    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    fun saveAccessToken(value: String) {
        authLocalDataSource.accessToken = value
    }

    fun saveAutoLoginState(value: Boolean) {
        authLocalDataSource.isAutoLogin = value
    }
}