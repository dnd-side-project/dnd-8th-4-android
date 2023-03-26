package com.dnd_8th_4_android.wery.presentation.ui.alert.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertNotificationData
import com.dnd_8th_4_android.wery.domain.repository.AlertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlertNotificationViewModel @Inject constructor(private val alertRepository: AlertRepository) :
    ViewModel() {

    private val _notificationList =
        MutableLiveData<List<ResponseAlertNotificationData.Data.NotificationInfo>>()
    val notificationList: LiveData<List<ResponseAlertNotificationData.Data.NotificationInfo>> =
        _notificationList

    fun getNotificationList() {
        viewModelScope.launch {
            kotlin.runCatching {
                alertRepository.getNotificationList()
            }.onSuccess {
                _notificationList.value = it.data.notificationInfoList
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}