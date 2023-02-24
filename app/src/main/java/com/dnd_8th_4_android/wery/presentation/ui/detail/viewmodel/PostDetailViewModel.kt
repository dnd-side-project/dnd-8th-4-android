package com.dnd_8th_4_android.wery.presentation.ui.detail.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import com.dnd_8th_4_android.wery.domain.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository,
) : ViewModel() {
    private val popupWindowImage = listOf(
        PopupWindowType.Type1.drawable,
        PopupWindowType.Type2.drawable,
        PopupWindowType.Type3.drawable,
        PopupWindowType.Type4.drawable,
        PopupWindowType.Type5.drawable,
        PopupWindowType.Type6.drawable
    )

    private val _emotionList = MutableLiveData<MutableList<ResponsePostDetailEmotionData.Data>>()
    val emotionList: LiveData<MutableList<ResponsePostDetailEmotionData.Data>> = _emotionList

    private val _emotionCount = MutableLiveData<Int>()
    val emotionCount: LiveData<Int> = _emotionCount

    private val _commentList = MutableLiveData<MutableList<ResponsePostDetailCommentData.Data.Content>>()
    val commentList: LiveData<MutableList<ResponsePostDetailCommentData.Data.Content>> = _commentList

    private val _commentCount = MutableLiveData<Int>()
    val commentCount: LiveData<Int> = _commentCount




    private val _isSelected = MutableLiveData(false)
    val isSelected: LiveData<Boolean> = _isSelected

    private val _isUpdateComment = MutableLiveData<MutableList<ResponsePostDetailCommentData.Data>>()
    val isUpdateComment: LiveData<MutableList<ResponsePostDetailCommentData.Data>> = _isUpdateComment

    private val _isEnabled = MutableLiveData<Boolean>()
    val isEnabled: LiveData<Boolean> = _isEnabled

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
    fun getComment(contentId: Int, page: Int) {
        viewModelScope.launch {
            kotlin.runCatching {
                detailRepository.getComment(contentId, page)
            }.onSuccess {
                _commentCount.value = it.data.content.size
                _commentList.value = it.data.content
                Log.e("태그", _commentList.value.toString())
            }.onFailure {
                Timber.tag("error").d(it.message.toString())
            }
        }
    }





    fun setUpdateEmotion(
        emotionPosition: Int,
        emotionList: List<ResponsePostDetailEmotionData.Data>,
        userImage: Int,
    ) {

    }

    fun setSelected() {
        _isSelected.value = _isSelected.value != true
    }

    fun setUnSelected() {
        _isSelected.value = false
    }

//    fun setUpdateComment(
//        commentList: List<ResponsePostDetailCommentData.Data>,
//        comment: String,
//        sticker: Int,
//    ) {
//        val commentCopyList = commentList.map {
//            it.copy()
//        } as MutableList<ResponsePostDetailCommentData.Data>
//
//        if (sticker != 0) {
//            commentCopyList.add(
//                ResponsePostDetailCommentData.Data(
//                    R.drawable.img_no_group,
//                    "User1",
//                    sticker,
//                    "",
//                    (LocalDateTime.now()).format(formatter)
//                )
//            )
//        } else {
//            commentCopyList.add(
//                ResponsePostDetailCommentData.Data(
//                    R.drawable.img_no_group,
//                    "User1",
//                    0,
//                    comment,
//                    (LocalDateTime.now()).format(formatter)
//                )
//            )
//        }
//        _isUpdateComment.value = commentCopyList
//    }

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