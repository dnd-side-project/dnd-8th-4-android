package com.dnd_8th_4_android.wery.presentation.ui.group.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupListData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData

class GroupViewModel : ViewModel() {
    private val _isExistGroup = MutableLiveData<Boolean>(true)
    val isExistGroup: LiveData<Boolean> = _isExistGroup

    var groupCount = MutableLiveData<Int>()

    private val _isUpdateGroup = MutableLiveData<MutableList<ResponseGroupData.Data>>()
    val isUpdateGroup: LiveData<MutableList<ResponseGroupData.Data>> = _isUpdateGroup

    private val _isUpdateBookmark = MutableLiveData<List<ResponseGroupListData.Data>>()
    val isUpdateBookmark: LiveData<List<ResponseGroupListData.Data>> = _isUpdateBookmark

    fun setUpdateBookmark(
        position: Int,
        groupBookmarkData: MutableList<ResponseGroupData.Data>,
        groupList: List<ResponseGroupListData.Data>,
    ) {
        val groupBookmarkCopyList = groupBookmarkData.map {
            it.copy()
        } as MutableList<ResponseGroupData.Data>

        val groupCopyList = groupList.map {
            it.copy()
        }

        groupCopyList[position].isSelected = !groupCopyList[position].isSelected

//        if (groupCopyList[position].isSelected) {
//            groupBookmarkCopyList.add(
//                ResponseGroupData.Data(
//                    groupCopyList[position].id,
//                    groupCopyList[position].image,
//                    groupCopyList[position].name
//                )
//            )
//        } else {
//            groupBookmarkCopyList.remove(
//                ResponseGroupData.Data(
//                    groupCopyList[position].id,
//                    groupCopyList[position].image,
//                    groupCopyList[position].name
//                )
//            )
//        }
        _isUpdateGroup.value = groupBookmarkCopyList
        _isUpdateBookmark.value = groupCopyList
    }
}