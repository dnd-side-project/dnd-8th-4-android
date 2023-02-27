package com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupInformationData
import com.dnd_8th_4_android.wery.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GroupInformationViewModel @Inject constructor(private val groupRepository: GroupRepository) :
    ViewModel() {

    val isSelectGroupId = MutableLiveData("")

    private val _groupList = MutableLiveData<ResponseGroupInformationData.Data>()
    val groupList: LiveData<ResponseGroupInformationData.Data> = _groupList

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getGroupInformation() {
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.getGroupInformation(isSelectGroupId.value!!.toInt())
            }.onSuccess {
                _groupList.value = it.data
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    fun deleteGroup() {
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.deleteGroup(isSelectGroupId.value!!.toInt())
            }.onSuccess {

            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    fun setLoading() {
        _isLoading.value = true
    }

    fun setUnLoading() {
        _isLoading.value = false
    }
}