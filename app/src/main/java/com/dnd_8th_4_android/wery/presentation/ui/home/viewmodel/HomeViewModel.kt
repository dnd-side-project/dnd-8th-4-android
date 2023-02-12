package com.dnd_8th_4_android.wery.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _isExistGroup = MutableLiveData<Boolean>(true)
    val isExistGroup: LiveData<Boolean> = _isExistGroup
}