package com.dnd_8th_4_android.wery.presentation.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _isExistGroup = MutableLiveData<Boolean>(true)
    val isExistGroup: LiveData<Boolean> = _isExistGroup

    private val _isUpdateList = MutableLiveData<List<ResponsePostData.Data>>()
    val isUpdateList: LiveData<List<ResponsePostData.Data>> = _isUpdateList

    fun setUpdateList(onePosition: Int, twoPosition: Int, list: List<ResponsePostData.Data>) {
        viewModelScope.launch {
            val list2 = list.map {
                it.copy()
            }
            list2[onePosition].isSelectedEmotion = twoPosition
            _isUpdateList.postValue(list2)
        }
    }
}