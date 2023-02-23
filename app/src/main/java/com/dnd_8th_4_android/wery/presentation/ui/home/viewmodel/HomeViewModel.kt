package com.dnd_8th_4_android.wery.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {
    private val _groupList = MutableLiveData<MutableList<ResponseGroupData.Data.GroupInfo>>()
    val groupList: LiveData<MutableList<ResponseGroupData.Data.GroupInfo>> = _groupList

    private val _postList = MutableLiveData<MutableList<ResponsePostData.Data.Content>>()
    val postList: LiveData<MutableList<ResponsePostData.Data.Content>> = _postList

    private val _isExistGroup = MutableLiveData<Boolean>()
    val isExistGroup: LiveData<Boolean> = _isExistGroup

    private val _isNoAccess = MutableLiveData<Boolean>()
    val isNoAccess: LiveData<Boolean> = _isNoAccess

    lateinit var groupAllIdList: MutableList<Int>

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    // 등록된 그룹 조회
    fun getSignGroup() {
        _isLoading.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepository.signGroup()
            }.onSuccess {
                _isExistGroup.value = it.data.existGroup

                if (it.data.existGroup) {
                    _groupList.value = it.data.groupInfoList

                    groupAllIdList = mutableListOf()
                    for (i in it.data.groupInfoList.indices) {
                        groupAllIdList.add(it.data.groupInfoList[i].id)
                    }
                    getGroupPost(groupAllIdList.joinToString(), 1)
                }
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
                _isNoAccess.value = false
            }
        }
    }

    // 그룹 게시글 조회
    fun getGroupPost(groupId: String, page: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepository.allGroupPost(groupId, page)
            }.onSuccess {
                _postList.value = it.data.content
                _isLoading.value = false
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    // 감정 이모지 설정
    fun setUpdateEmotion(
        isSelectGroupId: Int,
        contentId: Int,
        emotionStatus: RequestEmotionStatus,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepository.sendEmotionData(contentId, emotionStatus)
            }.onSuccess {
                // TODO 현재 page 처리
                if (it.data != null) {
                    if (isSelectGroupId != 0) {
                        getGroupPost(isSelectGroupId.toString(), 1)
                    } else {
                        getGroupPost(groupAllIdList.joinToString(), 1)
                    }
                } else {
                    setUpdateEmotion(isSelectGroupId, contentId, emotionStatus)
                }
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    fun setLoading() {
        _isLoading.value = true
    }
}