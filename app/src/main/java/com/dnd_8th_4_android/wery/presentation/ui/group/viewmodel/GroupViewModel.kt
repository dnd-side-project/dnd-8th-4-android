package com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupListData

class GroupViewModel : ViewModel() {
    private val _isExistGroup = MutableLiveData<Boolean>(true)
    val isExistGroup: LiveData<Boolean> = _isExistGroup

    var groupCount = MutableLiveData<Int>()

    private val _isUpdateBookmark = MutableLiveData<List<ResponseGroupListData.Data>>()
    val isUpdateBookmark: LiveData<List<ResponseGroupListData.Data>> = _isUpdateBookmark

    fun setUpdateBookmark(position: Int, groupList: List<ResponseGroupListData.Data>) {
        val groupCopyList = groupList.map {
            it.copy()
        }
        groupCopyList[position].isSelected = !groupCopyList[position].isSelected
        _isUpdateBookmark.value = groupCopyList
    }

}