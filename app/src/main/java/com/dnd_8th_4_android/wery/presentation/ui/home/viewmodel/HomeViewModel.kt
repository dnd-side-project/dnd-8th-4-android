package com.dnd_8th_4_android.wery.presentation.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {
    private var groupList = listOf<ResponseGroupData.Data.GroupInfo>()

    private val _isExistGroup = MutableLiveData<Boolean>()
    val isExistGroup: LiveData<Boolean> = _isExistGroup

    private val _isUpdateList = MutableLiveData<MutableList<ResponsePostData.Data>>()
    val isUpdateList: LiveData<MutableList<ResponsePostData.Data>> = _isUpdateList

    fun setUpdateList(position: Int, emotionPosition: Int, postList: List<ResponsePostData.Data>) {
        val postCopyList = postList.map {
            it.copy()
        } as MutableList<ResponsePostData.Data>

        postCopyList[position].isSelectedEmotion = emotionPosition

        when (emotionPosition) {
            PopupWindowType.Type1.emotionPosition -> {
                postCopyList[position].emotion.add(PopupWindowType.Type1.drawable)
            }
            PopupWindowType.Type2.emotionPosition -> {
                postCopyList[position].emotion.add(PopupWindowType.Type2.drawable)
            }
            PopupWindowType.Type3.emotionPosition -> {
                postCopyList[position].emotion.add(PopupWindowType.Type3.drawable)
            }
            PopupWindowType.Type4.emotionPosition -> {
                postCopyList[position].emotion.add(PopupWindowType.Type4.drawable)
            }
            PopupWindowType.Type5.emotionPosition -> {
                postCopyList[position].emotion.add(PopupWindowType.Type5.drawable)
            }
            PopupWindowType.Type6.emotionPosition -> {
                postCopyList[position].emotion.add(PopupWindowType.Type6.drawable)
            }
        }
        _isUpdateList.value = postCopyList
    }

    fun getSignGroup() {
        viewModelScope.launch {
            kotlin.runCatching {
                homeRepository.signGroup()
            }.onSuccess {
                _isExistGroup.value = it.data.existGroup
                groupList = it.data.groupInfoList

                Log.e("태그", _isExistGroup.value.toString())
                Log.e("태그", groupList.toString())
            }.onFailure {
                Log.e("태그", it.message.toString())
            }
        }
    }
}