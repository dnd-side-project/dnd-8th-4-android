package com.dnd_8th_4_android.wery.presentation.ui.mission.sticker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseSticker
import com.dnd_8th_4_android.wery.domain.repository.MissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StickerViewModel @Inject constructor(private val missionRepository: MissionRepository) :
    ViewModel() {

    private val _missionStatusList = MutableLiveData<ResponseSticker.Data>()
    val missionStatusList: LiveData<ResponseSticker.Data> = _missionStatusList

    fun getMissionStatus() {
        viewModelScope.launch {
            kotlin.runCatching {
                missionRepository.getMissionStatus()
            }.onSuccess {
                _missionStatusList.value = it.data
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}