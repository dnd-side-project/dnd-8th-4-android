package com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupListData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(private val groupRepository: GroupRepository) :
    ViewModel() {

    private val _isExistGroup = MutableLiveData<Boolean>(true)
    val isExistGroup: LiveData<Boolean> = _isExistGroup

    private val _bookmarkList = MutableLiveData<MutableList<ResponseBookmarkData.Data>>()
    val bookmarkList: LiveData<MutableList<ResponseBookmarkData.Data>> = _bookmarkList


    var groupCount = MutableLiveData<Int>()

    private val _isUpdateGroup = MutableLiveData<MutableList<ResponseGroupData.Data>>()
    val isUpdateGroup: LiveData<MutableList<ResponseGroupData.Data>> = _isUpdateGroup

    private val _isUpdateBookmark = MutableLiveData<List<ResponseGroupListData.Data>>()
    val isUpdateBookmark: LiveData<List<ResponseGroupListData.Data>> = _isUpdateBookmark

    fun getBookmarkList() {
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.getBookmarkList()
            }.onSuccess {
                _isExistGroup.value = it.data.isNotEmpty()
                _bookmarkList.value = it.data
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    fun setUpdateBookmark(
        position: Int,
        groupBookmarkData: MutableList<ResponseGroupData.Data.GroupInfo>,
        groupList: List<ResponseGroupListData.Data>,
    ) {

    }
}