package com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel

import android.util.Log
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

    private val _isExistGroup = MutableLiveData<Boolean>()
    val isExistGroup: LiveData<Boolean> = _isExistGroup

    private val _bookmarkList = MutableLiveData<MutableList<ResponseBookmarkData.Data>>()
    val bookmarkList: LiveData<MutableList<ResponseBookmarkData.Data>> = _bookmarkList

    private val _groupList = MutableLiveData<MutableList<ResponseGroupData.Data.GroupInfo>>()
    val groupList: LiveData<MutableList<ResponseGroupData.Data.GroupInfo>> = _groupList


    private val _isUpdateGroup = MutableLiveData<MutableList<ResponseGroupData.Data>>()
    val isUpdateGroup: LiveData<MutableList<ResponseGroupData.Data>> = _isUpdateGroup

    // 즐겨찾기한 그룹 목록 조회
    fun getBookmarkList() {
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.getBookmarkList()
            }.onSuccess {
                if (it.data.isNotEmpty()) {
                    _isExistGroup.value = true
                    _bookmarkList.value = it.data
                } else {
                    _isExistGroup.value = false
                }
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    // 등록된 그룹 조회
    fun getSignGroup() {
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.signGroup()
            }.onSuccess {
                _groupList.value = it.data.groupInfoList
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