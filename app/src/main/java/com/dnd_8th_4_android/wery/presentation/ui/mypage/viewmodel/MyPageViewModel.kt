package com.dnd_8th_4_android.wery.presentation.ui.mypage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyInfo
import com.dnd_8th_4_android.wery.domain.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val myPageRepository: MyPageRepository) :
    ViewModel() {

    private val _myInfoData = MutableLiveData<ResponseMyInfo.Data>()
    val myInfoData: LiveData<ResponseMyInfo.Data> = _myInfoData

    fun getMyInfo() {
        viewModelScope.launch {
            kotlin.runCatching {
                myPageRepository.getMyInfo()
            }.onSuccess {
                _myInfoData.value = it.data
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}