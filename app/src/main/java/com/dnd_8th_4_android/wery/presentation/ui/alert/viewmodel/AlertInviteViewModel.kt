package com.dnd_8th_4_android.wery.presentation.ui.alert.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertInviteData
import com.dnd_8th_4_android.wery.domain.repository.AlertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlertInviteViewModel @Inject constructor(private val alertRepository: AlertRepository) :
    ViewModel() {

    private val _inviteList = MutableLiveData<MutableList<ResponseAlertInviteData.Data.NotificationInfoList>>()
    val inviteList: LiveData<MutableList<ResponseAlertInviteData.Data.NotificationInfoList>> = _inviteList

    private val _isToastMessage: MutableLiveData<Boolean> = MutableLiveData()
    val isToastMessage: LiveData<Boolean> = _isToastMessage

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getInvite() {
        _isLoading.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                alertRepository.getInvite()
            }.onSuccess {
                _inviteList.value = it.data.notificationInfoList
                _isLoading.value = false
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    fun setAccept(groupId: Int, notificationId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                alertRepository.setAccept(groupId, notificationId)
            }.onSuccess {
                if(it.code == 0) {
                    _isToastMessage.value = true
                    getInvite()
                }
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}