package com.dnd_8th_4_android.wery.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _isUpdateList = MutableLiveData<MutableList<ResponsePostData.Data.Content>>()
    val isUpdateList: LiveData<MutableList<ResponsePostData.Data.Content>> = _isUpdateList

    fun setUpdateList(
        position: Int,
        emotionPosition: Int,
        postList: MutableList<ResponsePostData.Data.Content>,
    ) {
//        val postCopyList = postList.map {
//            it.copy()
//        } as MutableList<ResponsePostData.Data>
//
//        postCopyList[position].isSelectedEmotion = emotionPosition
//
//        when (emotionPosition) {
//            PopupWindowType.Type1.emotionPosition -> {
//                postCopyList[position].emotion.add(PopupWindowType.Type1.drawable)
//            }
//            PopupWindowType.Type2.emotionPosition -> {
//                postCopyList[position].emotion.add(PopupWindowType.Type2.drawable)
//            }
//            PopupWindowType.Type3.emotionPosition -> {
//                postCopyList[position].emotion.add(PopupWindowType.Type3.drawable)
//            }
//            PopupWindowType.Type4.emotionPosition -> {
//                postCopyList[position].emotion.add(PopupWindowType.Type4.drawable)
//            }
//            PopupWindowType.Type5.emotionPosition -> {
//                postCopyList[position].emotion.add(PopupWindowType.Type5.drawable)
//            }
//            PopupWindowType.Type6.emotionPosition -> {
//                postCopyList[position].emotion.add(PopupWindowType.Type6.drawable)
//            }
//        }
//        _isUpdateList.value = postCopyList
    }

    fun getSignGroup() {
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepository.signGroup()
            }.onSuccess {
                _isExistGroup.value = it.data.existGroup

                if (it.data.existGroup) {
                    _groupList.value = it.data.groupInfoList

                    val groupIdList = mutableListOf<Int>()
                    for (i in it.data.groupInfoList.indices) {
                        groupIdList.add(it.data.groupInfoList[i].id)
                    }

                    getAllGroupPost(groupIdList.joinToString(), 1)
                }
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    fun setPostList(value: MutableList<ResponsePostData.Data.Content>) {
        _postList.value = value
    }

    private fun getAllGroupPost(groupId: String, page: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepository.allGroupPost(groupId, page)
            }.onSuccess {
                _postList.value = it.data.content
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}