package com.dnd_8th_4_android.wery.presentation.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMission

class MapViewModel : ViewModel() {
    var myCurrentLatitude = MutableLiveData<Double>()
    var myCurrentLongitude = MutableLiveData<Double>()

    private val _feedList = MutableLiveData<MutableList<ResponseMission>>()
    val feedList: LiveData<MutableList<ResponseMission>> = _feedList

    private val _missionList = MutableLiveData<MutableList<ResponseMission>>()
    val missionList: LiveData<MutableList<ResponseMission>> = _missionList

    fun getMissionList():MutableList<ResponseMission> {
        // 임시값 1, 2, 3 넣어보기
        _missionList.value!!.apply {
            add(ResponseMission(33.450705,126.570677))
            add(ResponseMission(33.450936,126.569477))
            add(ResponseMission(33.450879,126.569940))
            add(ResponseMission(33.450705,126.570738))
        }
        return _missionList.value!!
    }
}