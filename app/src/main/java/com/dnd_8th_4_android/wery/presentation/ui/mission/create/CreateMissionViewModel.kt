package com.dnd_8th_4_android.wery.presentation.ui.mission.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateMissionViewModel : ViewModel() {
    var missionNameTxt = MutableLiveData<String>()

    private var _selectedPlaceTxt = MutableLiveData<String>()
    val selectedPlaceTxt: LiveData<String> = _selectedPlaceTxt

    private var _starDateTxt = MutableLiveData<String>()
    val starDateTxt: LiveData<String> = _starDateTxt

    private var _endDateTxt = MutableLiveData<String>()
    val endDateTxt: LiveData<String> = _endDateTxt

    fun setSelectedPlace(stringValue: String) {
        _selectedPlaceTxt.value = stringValue
    }

    fun setTodayDate() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val startFormatted = current.format(formatter)
        val endFormatted = current.plusDays(7).format(formatter)

        _starDateTxt.value = startFormatted
        _endDateTxt.value = endFormatted
    }

    fun setSelectedDate() {

    }
}