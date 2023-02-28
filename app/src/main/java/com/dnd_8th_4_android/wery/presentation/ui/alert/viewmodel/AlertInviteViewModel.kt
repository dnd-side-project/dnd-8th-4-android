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

    fun getInvite() {
        viewModelScope.launch {
            kotlin.runCatching {
                alertRepository.getInvite()
            }.onSuccess {
                _inviteList.value = it.data.notificationInfoList
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}