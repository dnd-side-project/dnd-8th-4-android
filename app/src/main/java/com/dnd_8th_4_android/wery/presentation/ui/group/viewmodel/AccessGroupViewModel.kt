package com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupMissionData
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccessGroupViewModel @Inject constructor(private val groupRepository: GroupRepository) :
    ViewModel() {

    private val _isExistMission = MutableLiveData<Boolean>()
    val isExistMission: LiveData<Boolean> = _isExistMission

    private val _postList = MutableLiveData<MutableList<ResponsePostData.Data.Content>>()
    val postList: LiveData<MutableList<ResponsePostData.Data.Content>> = _postList

    val isSelectGroupId = MutableLiveData("")

    private val _missionList = MutableLiveData<MutableList<ResponseGroupMissionData.Data>>()
    val missionList: LiveData<MutableList<ResponseGroupMissionData.Data>> = _missionList

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSelectedBookmark = MutableLiveData<Boolean>()
    val isSelectedBookmark: LiveData<Boolean> = _isSelectedBookmark

    // 그룹 게시글 조회
    fun getGroupPost() {
        _isLoading.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.allGroupPost(isSelectGroupId.value!!.toInt(), 1)
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
        contentId: Int,
        position: Int,
        emotionStatus: RequestEmotionStatus,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.sendEmotionData(contentId, emotionStatus)
            }.onSuccess {
                if (it.data != null) {
                    getGroupPost()
                } else {
                    if (_postList.value!![position].emotionStatus != emotionStatus.emotionStatus) {
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

    // 미션 목록 조회
    fun getMission() {
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.getMission(isSelectGroupId.value!!.toInt())
            }.onSuccess {
                if (it.data.isNotEmpty()) {
                    _isExistMission.value = true
                    _missionList.value = it.data
                } else {
                    _isExistMission.value = false
                }
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    // 그룹 즐겨찾기 등록
    fun setBookmark() {
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.setBookmark(isSelectGroupId.value!!.toInt())
            }.onSuccess {
                _isSelectedBookmark.value = it.data.groupStarYn == "ADD"
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    fun initBookmark(value: Boolean) {
        _isSelectedBookmark.value = value
    }
}