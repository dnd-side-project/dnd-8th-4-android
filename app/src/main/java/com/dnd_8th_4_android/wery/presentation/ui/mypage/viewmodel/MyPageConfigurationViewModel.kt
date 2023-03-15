package com.dnd_8th_4_android.wery.presentation.ui.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource
import com.dnd_8th_4_android.wery.domain.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageConfigurationViewModel @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val myPageRepository: MyPageRepository
) :
    ViewModel() {

    fun removeAutoLoginState() {
        authLocalDataSource.isAutoLogin = false
    }

    fun setDeleteAccount() {
        viewModelScope.launch {
            kotlin.runCatching {
                myPageRepository.deleteAccount()
            }.onSuccess {
                authLocalDataSource.isAutoLogin = false
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}