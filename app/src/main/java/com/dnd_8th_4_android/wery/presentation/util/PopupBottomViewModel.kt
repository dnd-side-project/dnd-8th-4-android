package com.dnd_8th_4_android.wery.presentation.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.domain.repository.PopupBottomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PopupBottomViewModel @Inject constructor(private val popupBottomRepository: PopupBottomRepository) :
    ViewModel() {

    private val _isSelectedBookmark = MutableLiveData<Boolean>()
    val isSelectedBookmark: LiveData<Boolean> = _isSelectedBookmark

    fun setBookmark(contentId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                popupBottomRepository.setBookmark(contentId)
            }.onSuccess {
                _isSelectedBookmark.value = it.data != null
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    fun setOnBookmark(value: Boolean) {
        _isSelectedBookmark.value = value
    }

    fun setPostDelete(contentId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                popupBottomRepository.setPostDelete(contentId)
            }.onSuccess {

            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}