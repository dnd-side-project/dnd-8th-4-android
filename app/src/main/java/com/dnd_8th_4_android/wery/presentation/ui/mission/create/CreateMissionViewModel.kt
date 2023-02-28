package com.dnd_8th_4_android.wery.presentation.ui.mission.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.mission.RequestCreateMissionData
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseGroupList
import com.dnd_8th_4_android.wery.domain.repository.MissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CreateMissionViewModel @Inject constructor(private val missionRepository: MissionRepository) :
    ViewModel() {
    var missionNameTxt = MutableLiveData<String>()
    var selectedGroup = MutableLiveData<String>()

    var selectedGroupState = MutableLiveData<Boolean>(false)

    var missionGroupState = MutableLiveData<Boolean>(false)
    var missionNameState = MutableLiveData<Boolean>(false)
    var missionPlaceState = MutableLiveData<Boolean>(false)

    private val _groupId: MutableLiveData<Long> = MutableLiveData()
    val groupId: LiveData<Long> = _groupId

    private val _groupListData = MutableLiveData<List<ResponseGroupList.ResultGroupList>>()
    var groupListData: LiveData<List<ResponseGroupList.ResultGroupList>> = _groupListData

    private var _selectedPlaceTxt = MutableLiveData<String>()
    val selectedPlaceTxt: LiveData<String> = _selectedPlaceTxt

    private var _starDateTxt = MutableLiveData<String>()
    val starDateTxt: LiveData<String> = _starDateTxt

    private var _endDateTxt = MutableLiveData<String>()
    val endDateTxt: LiveData<String> = _endDateTxt

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setSelectedPlace(stringValue: String) {
        _selectedPlaceTxt.value = stringValue
    }

    fun getGroupList() {
        viewModelScope.launch {
            missionRepository.getMyGroupList().onSuccess {
                _groupListData.value = it.data
            }
        }
    }

    fun setTodayDate() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val startFormatted = current.format(formatter)
        val endFormatted = current.plusWeeks(1).format(formatter)

        _starDateTxt.value = startFormatted
        _endDateTxt.value = endFormatted
    }

    fun setSelectedDate(year: Int, month: Int, day: Int) {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        _starDateTxt.value = LocalDate.of(year, month, day).format(formatter)
        _endDateTxt.value = LocalDate.of(year, month, day).plusWeeks(1).format(formatter)
    }

    fun postMission(body: RequestCreateMissionData) {
        viewModelScope.launch {
            kotlin.runCatching {
                missionRepository.createMission(body)
            }.onSuccess {
                _isLoading.value = false
            }.onFailure {
                _isLoading.value = false
            }
        }
    }

    fun getRequestBodyData(): RequestCreateMissionData {
        return RequestCreateMissionData(
            missionName = missionNameTxt.value!!,
            missionNote = null,
            groupId = 0,
            existPeriod = false,
            missionStartDate = null,
            missionEndDate = null,
            missionLocationName = _selectedPlaceTxt.value!!,
            latitude = 0.2,
            longitude = 0.0,
            missionColor = 0
        )
    }

    fun setGroupId(idValue: Long) {
        _groupId.value = idValue
    }
}