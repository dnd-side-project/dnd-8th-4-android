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

    private val _postList = MutableLiveData<ResponsePostData.Data>()
    val postList: LiveData<ResponsePostData.Data> = _postList

    private val _isExistGroup = MutableLiveData<Boolean>(true)
    val isExistGroup: LiveData<Boolean> = _isExistGroup

    private val _isNoAccess = MutableLiveData<Boolean>()
    val isNoAccess: LiveData<Boolean> = _isNoAccess

    lateinit var groupAllIdList: MutableList<Int>

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    val isSelectGroupId = MutableLiveData(-1)

    // 등록된 그룹 조회
    fun getSignGroup() {
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepository.signGroup()
            }.onSuccess {
                _isExistGroup.value = it.data.existGroup

                if (it.data.existGroup) {
                    if (isSelectGroupId.value == -1) {
                        _groupList.value = it.data.groupInfoList

                        groupAllIdList = mutableListOf()
                        for (i in it.data.groupInfoList.indices) {
                            groupAllIdList.add(it.data.groupInfoList[i].id)
                        }
                    }
                    getGroupPost()
                }
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
                _isNoAccess.value = false
            }
        }
    }

    // 그룹 게시글 조회
    fun getGroupPost() {
        _isLoading.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                if (isSelectGroupId.value == -1) {
                    homeRepository.allGroupPost(groupAllIdList.joinToString(), 1)
                } else {
                    homeRepository.allGroupPost(
                        isSelectGroupId.value.toString(),
                        1
                    )
                }
            }.onSuccess {
                _postList.value = it.data
                _isLoading.value = false
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    // 감정 이모지 설정
    fun setUpdateEmotion(
        contentId: Int,
        position: Int,
        emotionStatus: RequestEmotionStatus,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepository.sendEmotionData(contentId, emotionStatus)
            }.onSuccess {
                if (it.data != null) {
                    getGroupPost()
                } else {
                    if (_postList.value!!.content[position].emotionStatus != emotionStatus.emotionStatus) {
                        setUpdateEmotion(contentId, position, emotionStatus)
                    } else {
                        getGroupPost()
                    }
                }
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}