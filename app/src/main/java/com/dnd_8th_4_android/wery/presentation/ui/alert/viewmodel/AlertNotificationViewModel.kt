package com.dnd_8th_4_android.wery.presentation.ui.alert.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertNotificationData
import com.dnd_8th_4_android.wery.data.remote.model.alert.ResponseAlertPostInfoData
import com.dnd_8th_4_android.wery.domain.repository.AlertRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlertNotificationViewModel @Inject constructor(private val alertRepository: AlertRepository) :
    ViewModel() {

    private val _isRead = MutableLiveData<Boolean>()
    val isRead: LiveData<Boolean> = _isRead

    private val _notificationList =
        MutableLiveData<List<ResponseAlertNotificationData.Data.NotificationInfo>>()
    val notificationList: LiveData<List<ResponseAlertNotificationData.Data.NotificationInfo>> =
        _notificationList

    private val _contentId = MutableLiveData<Int>()
    val contentId: LiveData<Int> = _contentId

    private val _notificationPostInfo = MutableLiveData<ResponseAlertPostInfoData.Data?>()
    val notificationPostInfo: LiveData<ResponseAlertPostInfoData.Data?> =
        _notificationPostInfo

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

    fun readNotification(notificationId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                alertRepository.readAlert(notificationId)
            }.onSuccess {
                _isRead.value = true
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    fun getAlertPostInfo(contentId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                alertRepository.getAlertPostInfo(contentId)
            }.onSuccess {
                _notificationPostInfo.value = it.data
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    fun setContentId(contentId: Int) {
        _contentId.value = contentId
    }

    fun getContentId(): Int {
        return _contentId.value!!
    }
}