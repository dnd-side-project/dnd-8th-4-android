package com.dnd_8th_4_android.wery.presentation.ui.onboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd_8th_4_android.wery.data.local.AuthLocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val localDataSource: AuthLocalDataSource) : ViewModel() {

    // indicator 현재 위치
    private var _currentPosition = MutableLiveData<Int>()
    val currentPosition: LiveData<Int> = _currentPosition

    // 현재 위치 설정
    fun setCurrentPos(posValue: Int) {
        _currentPosition.value = posValue
    }

    // 온보딩 상태 저장
    fun saveOnBoardingState() {
        localDataSource.hasOnboardDone = true
    }

}