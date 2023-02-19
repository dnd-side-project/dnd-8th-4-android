package com.dnd_8th_4_android.wery.presentation.ui.write.place.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseSearchPlace.Document
import com.dnd_8th_4_android.wery.domain.repository.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchPlaceViewModel @Inject constructor(private val placeRepository: PlaceRepository) :
    ViewModel() {

    private val _searchPlace = MutableLiveData<List<Document>>()
    var searchPlace: LiveData<List<Document>> = _searchPlace

    fun getSearchPlace(query: String) {
        viewModelScope.launch {
            placeRepository.searchPlace(query).onSuccess {
                _searchPlace.value = it.documents
            }
        }
    }
}