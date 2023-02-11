package com.dnd_8th_4_android.wery.presentation.ui.sign.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    val id = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    private var _checkState = MutableLiveData<Boolean>()
    val checkState: LiveData<Boolean> = _checkState


}