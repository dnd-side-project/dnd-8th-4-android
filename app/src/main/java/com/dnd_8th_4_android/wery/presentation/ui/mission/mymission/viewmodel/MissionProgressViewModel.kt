package com.dnd_8th_4_android.wery.presentation.ui.mission.mymission.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMyMissionCard
import com.dnd_8th_4_android.wery.domain.repository.MissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MissionProgressViewModel @Inject constructor(private val missionRepository: MissionRepository) :
    ViewModel() {

    private val _progressMissionList =
        MutableLiveData<List<ResponseMyMissionCard>>()
    val progressMissionList: LiveData<List<ResponseMyMissionCard>> = _progressMissionList

    fun getSearchPost() {
        viewModelScope.launch {
            kotlin.runCatching {
                missionRepository.getMyMissionList()
            }.onSuccess {
                _progressMissionList.value = it.data
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}