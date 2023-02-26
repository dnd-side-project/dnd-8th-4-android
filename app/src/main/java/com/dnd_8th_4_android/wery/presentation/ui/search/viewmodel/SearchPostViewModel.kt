package com.dnd_8th_4_android.wery.presentation.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.search.ResponsePostSearchData
import com.dnd_8th_4_android.wery.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchPostViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {

    private val _searchPostList =
        MutableLiveData<MutableList<ResponsePostSearchData.Data.Content>>()
    val searchPostList: LiveData<MutableList<ResponsePostSearchData.Data.Content>> = _searchPostList

    val searchKeyword = MutableLiveData("")

    fun getSearchPost(groupId: String) {
        // TODO 페이지 처리
        viewModelScope.launch {
            kotlin.runCatching {
                searchRepository.groupPostSearch(groupId, searchKeyword.value!!, 1)
            }.onSuccess {
                _searchPostList.value = it.data.content
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }
}