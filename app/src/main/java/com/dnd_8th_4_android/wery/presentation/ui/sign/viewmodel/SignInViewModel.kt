package com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource
import com.dnd_8th_4_android.wery.data.remote.model.sign.RequestSignInData
import com.dnd_8th_4_android.wery.data.remote.model.sign.ResponseSignInData
import com.dnd_8th_4_android.wery.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRepository: AuthRepository
) : ViewModel() {
    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    private val _signInData = MutableLiveData<ResponseSignInData>()
    var signInData: LiveData<ResponseSignInData> = _signInData

    fun getSignInData() {
        viewModelScope.launch {
            authRepository.loginUser(RequestSignInData(id.value!!, pw.value!!)).onSuccess {
                _signInData.value = it
                it.data?.let { result ->
                    saveAccessToken(result.atk)
                    saveUserId(result.id)
                }
            }
        }
    }

    fun saveAccessToken(value: String) {
        authLocalDataSource.accessToken = value
    }

    fun saveAutoLoginState(value: Boolean) {
        authLocalDataSource.isAutoLogin = value
    }

    fun saveUserId(value: Int) {
        authLocalDataSource.userId = value
    }
}