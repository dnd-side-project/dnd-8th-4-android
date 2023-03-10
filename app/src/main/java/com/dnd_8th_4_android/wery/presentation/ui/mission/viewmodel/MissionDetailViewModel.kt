package com.dnd_8th_4_android.wery.presentation.ui.mission.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.mission.RequestMissionCertifyData
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMissionDetailData
import com.dnd_8th_4_android.wery.domain.repository.MissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MissionDetailViewModel @Inject constructor(private val missionRepository: MissionRepository) :
    ViewModel() {

    private val _missionDetail = MutableLiveData<ResponseMissionDetailData.Data>()
    val missionDetail: LiveData<ResponseMissionDetailData.Data> = _missionDetail

    val isMissionId = MutableLiveData<Int>()

    var myCurrentLatitude = MutableLiveData<Double>()
    var myCurrentLongitude = MutableLiveData<Double>()

    private val _isCertify = MutableLiveData<Boolean>()
    val isCertify: LiveData<Boolean> = _isCertify

    fun getMissionDetail() {
        viewModelScope.launch {
            kotlin.runCatching {
                missionRepository.getMissionDetail(isMissionId.value!!)
            }.onSuccess {
                _missionDetail.value = it.data
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    fun missionDelete() {
        viewModelScope.launch {
            kotlin.runCatching {
                missionRepository.missionDelete(isMissionId.value!!)
            }.onSuccess {

            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    fun missionCertify(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                missionRepository.missionCertify(
                    RequestMissionCertifyData(
                        isMissionId.value!!,
                        groupId,
                        myCurrentLatitude.value!!,
                        myCurrentLongitude.value!!
                    )
                )
            }.onSuccess {
                _isCertify.value = it.data.locationCheck
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}