package com.dnd_8th_4_android.wery.presentation.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {
    var myCurrentLatitude = MutableLiveData<Double>()
    var myCurrentLongitude = MutableLiveData<Double>()
}