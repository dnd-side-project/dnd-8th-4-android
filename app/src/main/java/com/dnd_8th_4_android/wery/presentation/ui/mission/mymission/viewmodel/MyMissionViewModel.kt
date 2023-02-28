package com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMainMissionCard
import com.dnd_8th_4_android.wery.domain.repository.MissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyMissionViewModel @Inject constructor(private val missionRepository: MissionRepository) :
    ViewModel() {

    private val _progressMainMissionList =
        MutableLiveData<List<ResponseMainMissionCard.ResultMissionCard>>()
    val progressMainMissionList: LiveData<List<ResponseMainMissionCard.ResultMissionCard>> = _progressMainMissionList

    fun getMyMissionList() {
        viewModelScope.launch {
            kotlin.runCatching {
                missionRepository.getMainMissionList()
            }.onSuccess {
                _progressMainMissionList.value = it.data
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}