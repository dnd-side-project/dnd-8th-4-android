package com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(private val groupRepository: GroupRepository) :
    ViewModel() {

    private val _isExistGroup = MutableLiveData(true)
    val isExistGroup: LiveData<Boolean> = _isExistGroup

    private val _isNewNotification = MutableLiveData<Boolean>(true)
    val isNewNotification: LiveData<Boolean> = _isNewNotification

    private val _isExistBookmarkGroup = MutableLiveData(true)
    val isExistBookmarkGroup: LiveData<Boolean> = _isExistBookmarkGroup

    private val _bookmarkList = MutableLiveData<MutableList<ResponseBookmarkData.Data>>()
    val bookmarkList: LiveData<MutableList<ResponseBookmarkData.Data>> = _bookmarkList

    private val _groupList = MutableLiveData<MutableList<ResponseGroupData.Data.GroupInfo>>()
    val groupList: LiveData<MutableList<ResponseGroupData.Data.GroupInfo>> = _groupList

    private val _groupAllIdList = MutableLiveData<String>()
    val groupAllIdList: LiveData<String> = _groupAllIdList

    lateinit var groupIdList: MutableList<Int>

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    // 즐겨찾기한 그룹 목록 조회
    fun getBookmarkList() {
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.getBookmarkList()
            }.onSuccess {
                if (it.data.isNotEmpty()) {
                    _isExistBookmarkGroup.value = true
                    _bookmarkList.value = it.data
                } else {
                    _isExistBookmarkGroup.value = false
                }
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    // 등록된 그룹 조회
    fun getSignGroup() {
        _isLoading.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.signGroup()
            }.onSuccess {
                _isExistGroup.value = it.data.existGroup
                _isNewNotification.value = it.data.isNewNotification

                if (it.data.existGroup) {
                    _groupList.value = it.data.groupInfoList

                    groupIdList = mutableListOf()
                    for (i in it.data.groupInfoList.indices) {
                        groupIdList.add(it.data.groupInfoList[i].id)
                    }

                    _groupAllIdList.value = groupIdList.joinToString()
                }
                _isLoading.value = false
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    // 그룹 즐겨찾기 등록
    fun setBookmark(groupId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.setBookmark(groupId)
            }.onSuccess {
                getBookmarkList()
                getSignGroup()
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}