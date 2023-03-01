package com.dnd_8th_4_android.wery.presentation.ui.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.map.RequestMapMissionList
import com.dnd_8th_4_android.wery.data.remote.model.map.ResponseMapMissionList.ResultMapMission
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseSearchPlace
import com.dnd_8th_4_android.wery.domain.repository.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
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

    private val _feedList = MutableLiveData<MutableList<ResultMapMission>>()
    val feedList: LiveData<MutableList<ResultMapMission>> = _feedList

    private val _missionList = MutableLiveData<List<ResultMapMission>>()
    val missionList: LiveData<List<ResultMapMission>> = _missionList

    private val _isBottomDialogShowing = MutableLiveData<Boolean>()
    val isBottomDialogShowing: LiveData<Boolean> = _isBottomDialogShowing

    private val _searchResult = MutableLiveData<ResponseSearchPlace.Document>()
    val searchResult: LiveData<ResponseSearchPlace.Document> = _searchResult

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getMissionList(body: RequestMapMissionList) {
        _isLoading.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                mapRepository.getMapMissionList(body)
            }.onSuccess {
                _missionList.value = it.data
                _isLoading.value = false
                Log.d("kite",it.data.toString())
            }.onFailure {
                Timber.tag("kite").d(it.message.toString())
                Log.d("kite",it.message.toString())
                _isLoading.value = false
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
}