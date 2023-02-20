package com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccessGroupViewModel : ViewModel() {
    private val _isExistMission = MutableLiveData<Boolean>(true)
    val isExistMission: LiveData<Boolean> = _isExistMission

    private val _isMissionCount = MutableLiveData(0)
    val isMissionCount: LiveData<Int> = _isMissionCount
}