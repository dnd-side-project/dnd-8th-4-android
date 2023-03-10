package com.dnd_8th_4_android.wery.presentation.ui.post.place.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseSearchPlace.Document
import com.dnd_8th_4_android.wery.domain.repository.PlaceRepository
import com.dnd_8th_4_android.wery.presentation.di.OtherHttpClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchPlaceViewModel @Inject constructor(@OtherHttpClient private val placeRepository: PlaceRepository) :
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