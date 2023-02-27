package com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupInformationData
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseUserSearchData
import com.dnd_8th_4_android.wery.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(private val groupRepository: GroupRepository) :
    ViewModel() {

    private val _searchUserList = MutableLiveData<MutableList<ResponseUserSearchData.Data.UserSearchInfoList>>()
    val searchUserList: LiveData<MutableList<ResponseUserSearchData.Data.UserSearchInfoList>> = _searchUserList

    val searchKeyword = MutableLiveData("")

    fun getUserSearchList() {
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.getUserSearchList(searchKeyword.value!!)
            }.onSuccess {
                _searchUserList.value = it.data.userSearchInfoList
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}