package com.dnd_8th_4_android.wery.presentation.util

import androidx.lifecycle.ViewModel
import com.dnd_8th_4_android.wery.domain.repository.PopupBottomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopupRemoveViewModel @Inject constructor(private val popupBottomRepository: PopupBottomRepository) :
    ViewModel() {

}