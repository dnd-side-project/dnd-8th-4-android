package com.dnd_8th_4_android.wery.presentation.ui.mission.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateMissionViewModel : ViewModel() {
    var missionNameTxt = MutableLiveData<String>()

    private var _selectedPlaceTxt = MutableLiveData<String>()
    val selectedPlaceTxt: LiveData<String> = _selectedPlaceTxt

    fun setSelectedPlace(stringValue:String) {
        _selectedPlaceTxt.value = stringValue
    }
}