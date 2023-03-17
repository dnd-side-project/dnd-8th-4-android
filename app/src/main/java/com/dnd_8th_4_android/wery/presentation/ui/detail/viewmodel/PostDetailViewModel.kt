package com.dnd_8th_4_android.wery.presentation.ui.detail.viewmodel

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.data.remote.model.detail.*
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
) : ViewModel() {

    private val _postDetailList = MutableLiveData<ResponsePostDetailData.Data>()
    val postDetailList: LiveData<ResponsePostDetailData.Data> = _postDetailList

    private val _emotionList = MutableLiveData<MutableList<ResponsePostDetailEmotionData.Data>>()
    val emotionList: LiveData<MutableList<ResponsePostDetailEmotionData.Data>> = _emotionList

    private val _emotionCount = MutableLiveData<Int>()
    val emotionCount: LiveData<Int> = _emotionCount

    private val _commentList =
        MutableLiveData<MutableList<ResponsePostDetailCommentData.Data.Content>>()
    val commentList: LiveData<MutableList<ResponsePostDetailCommentData.Data.Content>> =
        _commentList

    private val _commentCount = MutableLiveData<Int>()
    val commentCount: LiveData<Int> = _commentCount

    private val _stickerList =
        MutableLiveData<MutableList<ResponsePostDetailStickerData.Data.StickerInfoList>>()
    val stickerList: LiveData<MutableList<ResponsePostDetailStickerData.Data.StickerInfoList>> =
        _stickerList

    private val _isSelected = MutableLiveData(false)
    val isSelected: LiveData<Boolean> = _isSelected

    private val _isEnabled = MutableLiveData<Boolean>()
    val isEnabled: LiveData<Boolean> = _isEnabled

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _pageNumber = MutableLiveData(0)

    private val _isNoData = MutableLiveData(false)
    val isNoData: LiveData<Boolean> = _isNoData

    // 피드 상세보기
    fun getPostDetail(contentId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                detailRepository.getPostDetail(contentId)
            }.onSuccess {
                _postDetailList.value = it.data
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    // 피드 공감 조회
    fun getEmotion(contentId: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                detailRepository.getEmotion(contentId)
            }.onSuccess {
                _emotionCount.value = it.data.size
                _emotionList.value = it.data
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    // 피드 댓글 조회
    fun getComment(contentId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            kotlin.runCatching {
                detailRepository.getComment(contentId, _pageNumber.value!!)
            }.onSuccess {
                if (_pageNumber.value == 1) {
                    _commentList.value = it.data.content
                } else {
                    val commentList = _commentList.value!!.map { currentList ->
                        currentList.copy()
                    } as MutableList<ResponsePostDetailCommentData.Data.Content>

                    commentList.addAll(it.data.content)

                    _commentList.value = commentList
                }
                _isNoData.value = it.data.content.size != _pageNumber.value!! * 10
                _commentCount.value = _commentList.value!!.size
                _isLoading.value = false
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    // 감정 이모지 설정
    fun setUpdateEmotion(
        contentId: Int,
        emotion: RequestEmotionStatus,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                detailRepository.sendEmotionData(contentId, emotion)
            }.onSuccess {
                getEmotion(contentId)
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    // 댓글 작성
    fun setUpdateComment(
        contentId: Int,
        commentNote: RequestPostDetailCommentNote,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                detailRepository.sendContent(contentId, commentNote)
            }.onSuccess {
                getComment(contentId)
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    // 스티커 작성
    fun setUpdateSticker(
        contentId: Int,
        stickerId: RequestPostDetailStickerId,
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                detailRepository.sendSticker(contentId, stickerId)
            }.onSuccess {
                getComment(contentId)
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    // 스티커
    fun getSticker() {
        viewModelScope.launch {
            kotlin.runCatching {
                detailRepository.getSticker()
            }.onSuccess {
                if (it.data.isNotEmpty()) {
                    _stickerList.value = it.data[0].stickerInfoList
                }
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }

    fun setPageNumber(pageNumber: Int) {
        _pageNumber.value = pageNumber
    }

    fun setUpPageNumber() {
        _pageNumber.value = _pageNumber.value!! + 1
    }

    fun setSelected() {
        _isSelected.value = _isSelected.value != true
    }

    fun setUnSelected() {
        _isSelected.value = false
    }

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            _isEnabled.value = p0?.length!! > 0
        }

        override fun afterTextChanged(p0: Editable?) {

        }
    }
}