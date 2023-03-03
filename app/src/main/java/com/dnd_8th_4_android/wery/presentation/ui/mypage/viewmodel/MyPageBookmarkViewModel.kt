package com.dnd_8th_4_android.wery.presentation.ui.mypage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.mypage.ResponseMyBookmarkData
import com.dnd_8th_4_android.wery.domain.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageBookmarkViewModel @Inject constructor(private val myPageRepository: MyPageRepository): ViewModel() {

    private val _myBookmarkList = MutableLiveData<MutableList<ResponseMyBookmarkData.Data.Content>>()
    val myBookmarkData: LiveData<MutableList<ResponseMyBookmarkData.Data.Content>> = _myBookmarkList

    // TODO 페이징 처리
    fun getMyBookmarkList() {
        viewModelScope.launch {
            kotlin.runCatching {
                myPageRepository.getMyBookmarkList(1)
            }.onSuccess {
                _myBookmarkList.value = it.data.content
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}