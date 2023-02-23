package com.dnd_8th_4_android.wery.presentation.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapMission

class MapViewModel : ViewModel() {
    var myCurrentLatitude = MutableLiveData<Double>()
    var myCurrentLongitude = MutableLiveData<Double>()

    private val _feedList = MutableLiveData<MutableList<ResponseMapMission>>()
    val feedList: LiveData<MutableList<ResponseMapMission>> = _feedList

    private val _missionList = MutableLiveData<MutableList<ResponseMapMission>>()
    val missionList: LiveData<MutableList<ResponseMapMission>> = _missionList

    private val _filterType = MutableLiveData<Int>(0)
    val filterType: LiveData<Int> = _filterType

    private val _isBottomDialogShowing = MutableLiveData<Boolean>()
    val isBottomDialogShowing: LiveData<Boolean> = _isBottomDialogShowing

    fun getMissionList(): MutableList<ResponseMapMission> {
        // 임시값 1, 2, 3 넣어보기
        _missionList.value!!.apply {
            add(ResponseMapMission(33.450705, 126.570677))
            add(ResponseMapMission(33.450936, 126.569477))
            add(ResponseMapMission(33.450879, 126.569940))
            add(ResponseMapMission(33.450705, 126.570738))
        }
        return _missionList.value!!
    }

    fun getFeedList():MutableList<ResponseMapMission> {
        // 임시값 1, 2, 3 넣어보기
        _feedList.value!!.apply {
            add(ResponseMapMission(33.450705,126.570677))
            add(ResponseMapMission(33.450936, 126.569477))
            add(ResponseMapMission(33.450879, 126.569940))
            add(ResponseMapMission(33.450705, 126.570738))
        }
        return _feedList.value!!
    }

    fun setFilterType(type: Int) {
        _filterType.value = type
    }

    fun setBottomDialogShowingState(state: Boolean) {
        _isBottomDialogShowing.value = state
    }
}