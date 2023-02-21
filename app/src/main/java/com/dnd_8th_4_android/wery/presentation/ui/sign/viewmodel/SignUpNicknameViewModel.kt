package com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.sign.RequestSignUpData
import com.dnd_8th_4_android.wery.data.remote.model.sign.ResponseSignInData
import com.dnd_8th_4_android.wery.data.remote.model.sign.ResponseSignUpData
import com.dnd_8th_4_android.wery.domain.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpNicknameViewModel @Inject constructor(private val signUpRepository: SignUpRepository) :
    ViewModel() {
    val signUpNickname = MutableLiveData<String>()

    private val _isEnabled = MutableLiveData<Boolean>()
    val isEnabled: LiveData<Boolean> = _isEnabled

    private val _textCount = MutableLiveData(0)
    val textCount: LiveData<Int> = _textCount

    private val _isExisted = MutableLiveData<Boolean>()
    val isExisted: LiveData<Boolean> = _isExisted

    private val _signUpSuccess = MutableLiveData<Boolean>()
    val signUpSuccess: LiveData<Boolean> = _signUpSuccess

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            _textCount.value = p0?.length
            _isEnabled.value = p0?.length!! > 0
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }

    fun signUp(body: RequestSignUpData) {
        viewModelScope.launch {
            kotlin.runCatching {
                signUpRepository.signUp(body)
            }.onSuccess {
                _isExisted.value = it.data == null
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}