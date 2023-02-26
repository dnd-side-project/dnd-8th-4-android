package com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _isExistMission = MutableLiveData<Boolean>(true)
    val isExistMission: LiveData<Boolean> = _isExistMission

    private val _isMissionCount = MutableLiveData(0)
    val isMissionCount: LiveData<Int> = _isMissionCount

    private val _postList = MutableLiveData<MutableList<ResponsePostData.Data.Content>>()
    val postList: LiveData<MutableList<ResponsePostData.Data.Content>> = _postList

    val isSelectGroupId = MutableLiveData(-1)

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    // 그룹 게시글 조회
    fun getGroupPost() {
        viewModelScope.launch {
            kotlin.runCatching {
                groupRepository.allGroupPost(isSelectGroupId.value!!, 1)
            }.onSuccess {
                _postList.value = it.data.content
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

    fun setLoading() {
        _isLoading.value = true
    }

    fun setUnLoading() {
        _isLoading.value = false
    }
}