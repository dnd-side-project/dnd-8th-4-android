package com.dnd_8th_4_android.wery.presentation.ui.write.upload.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseGroupList
import com.dnd_8th_4_android.wery.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WritingViewModel @Inject constructor(private val postRepository: PostRepository) :
    ViewModel() {

    private var _photoCnt = MutableLiveData<Int>(0)
    val photoCnt: LiveData<Int> = _photoCnt

    var noteTxt = MutableLiveData<String>()
    var selectedPlace = MutableLiveData<String>()
    var selectedGroup = MutableLiveData<String>()
    var selectedGroupState = MutableLiveData<Boolean>(false)

    private val _groupListData = MutableLiveData<List<ResponseGroupList.ResultGroupList>>()
    var groupListData: LiveData<List<ResponseGroupList.ResultGroupList>> = _groupListData

    fun setPhotoCnt(cntValue: Int) {
        _photoCnt.value = cntValue
    }

    fun getGroupList() {
        viewModelScope.launch {
            postRepository.getMyGroupList().onSuccess {
                _groupListData.value = it.data
            }
        }
    }

}