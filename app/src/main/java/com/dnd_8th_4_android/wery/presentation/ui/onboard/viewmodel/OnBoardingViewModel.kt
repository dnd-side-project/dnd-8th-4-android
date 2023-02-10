package com.dnd_8th_4_android.wery.presentation.ui.onboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val localDataSource: AuthLocalDataSource) : ViewModel() {

    private var _currentPosition = MutableLiveData<Int>()
    val currentPosition: LiveData<Int> = _currentPosition

    fun setCurrentPos(posValue: Int) {
        _currentPosition.value = posValue
    }

    fun saveOnBoardingState() {
        localDataSource.hasOnboardDone = true
    }

}