package com.dnd_8th_4_android.wery.presentation.ui.write.upload.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class WritingViewModel : ViewModel() {

    private var _photoCnt = MutableLiveData<Int>(0)
    val photoCnt: LiveData<Int> = _photoCnt

    var noteTxt = MutableLiveData<String>()
    var selectedPlace = MutableLiveData<String>()
    var selectedGroup = MutableLiveData<String>()
    var selectedGroupState = MutableLiveData<Boolean>(false)

    fun setPhotoCnt(cntValue: Int) {
        _photoCnt.value = cntValue
    }

}