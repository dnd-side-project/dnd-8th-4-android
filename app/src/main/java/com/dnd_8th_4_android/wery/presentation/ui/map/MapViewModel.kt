package com.dnd_8th_4_android.wery.presentation.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.map.*
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapMissionList.ResultMapMission
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseSearchPlace
import com.dnd_8th_4_android.wery.domain.repository.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val mapRepository: MapRepository) : ViewModel() {

    // 현재 위치 좌표
    var myCurrentLatitude = MutableLiveData<Double>()
    var myCurrentLongitude = MutableLiveData<Double>()

    // MapBounds 좌표
    var startLatitude = MutableLiveData<Double>() // 좌하단
    var startLongitude = MutableLiveData<Double>() // 좌하단
    var endLatitude = MutableLiveData<Double>() // 우상단
    var endLongitude = MutableLiveData<Double>() // 우상단

    var searchPlaceTxt = MutableLiveData<String>()

    private val _filterType = MutableLiveData<Int>(0)
    val filterType: LiveData<Int> = _filterType

    private val _mapSettingState = MutableLiveData<Boolean>(true)
    val mapSettingState: LiveData<Boolean> = _mapSettingState

    private val _feedList =
        MutableLiveData<List<ResponseMapFeedList.ResultMapFeedData>>()
    val feedList: LiveData<List<ResponseMapFeedList.ResultMapFeedData>> = _feedList

    private val _missionList = MutableLiveData<List<ResultMapMission>>()
    val missionList: LiveData<List<ResultMapMission>> = _missionList

    private val _feedListData = MutableLiveData<ResponseMapFeedData>()
    val feedListData: LiveData<ResponseMapFeedData> = _feedListData

    private val _missionCardData = MutableLiveData<ResponseMapMissionData>()
    val missionCardData: LiveData<ResponseMapMissionData> = _missionCardData

    private val _isBottomDialogShowing = MutableLiveData<Boolean>()
    val isBottomDialogShowing: LiveData<Boolean> = _isBottomDialogShowing

    private val _searchResult = MutableLiveData<ResponseSearchPlace.Document>()
    val searchResult: LiveData<ResponseSearchPlace.Document> = _searchResult

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFeedList() {
        viewModelScope.launch {
            _isLoading.value = true
            kotlin.runCatching {
                mapRepository.getMapFeedList(startLatitude.value!!,startLongitude.value!!, endLatitude.value!!,endLongitude.value!!)
            }.onSuccess {
                _feedList.value = it.data
                _isLoading.value = false
            }
        }
    }

    fun getMissionList(body: RequestMapMissionList) {
        viewModelScope.launch {
            kotlin.runCatching {
                mapRepository.getMapMissionList(body)
            }.onSuccess {
                _missionList.value = it.data
            }
        }
    }

    fun getMissionCardData(missionId:Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                mapRepository.getMissionData(missionId)
            }.onSuccess {
                _missionCardData.value = it
            }
        }
    }

    fun getFeedData(location:String) {
        viewModelScope.launch {
            kotlin.runCatching {
                mapRepository.getFeedData(location)
            }.onSuccess {
                _feedListData.value = it
            }
        }
    }

    fun setFilterType(type: Int) {
        _filterType.value = type
    }

    fun setBottomDialogShowingState(state: Boolean) {
        _isBottomDialogShowing.value = state
    }

    fun setSearchResult(data: ResponseSearchPlace.Document) {
        _searchResult.value = data
    }

    fun getCurrentMapBounds(): RequestMapMissionList {
        return RequestMapMissionList(
            startLatitude = startLatitude.value!!,
            startLongitude = startLongitude.value!!,
            endLatitude = endLatitude.value!!,
            endLongitude = endLongitude.value!!
        )
    }

    fun getMapSettingState(): Boolean {
        return _mapSettingState.value!!
    }

    fun setMapSettingState(state: Boolean) {
        _mapSettingState.value = state
    }
}