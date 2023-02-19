package com.dnd_8th_4_android.wery.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType

class HomeViewModel : ViewModel() {
    private val _isExistGroup = MutableLiveData<Boolean>(true)
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
}