package com.dnd_8th_4_android.wery.presentation.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd_8th_4_android.wery.R
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailCommentData
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PostDetailViewModel : ViewModel() {
    private var oldEmotionData = ResponsePostDetailEmotionData.Data(0, 0)
    private val popupWindowImage = listOf(
        PopupWindowType.Type1.drawable,
        PopupWindowType.Type2.drawable,
        PopupWindowType.Type3.drawable,
        PopupWindowType.Type4.drawable,
        PopupWindowType.Type5.drawable,
        PopupWindowType.Type6.drawable
    )

    private val _isUpdateEmotion = MutableLiveData<MutableList<ResponsePostDetailEmotionData.Data>>()
    val isUpdateEmotion: LiveData<MutableList<ResponsePostDetailEmotionData.Data>> = _isUpdateEmotion

    private val _emotionCount = MutableLiveData<Int>()
    val emotionCount: LiveData<Int> = _emotionCount

    private val _isSelected = MutableLiveData(false)
    val isSelected: LiveData<Boolean> = _isSelected

    private val _isUpdateComment = MutableLiveData<MutableList<ResponsePostDetailCommentData.Data>>()
    val isUpdateComment: LiveData<MutableList<ResponsePostDetailCommentData.Data>> = _isUpdateComment

    private val formatter = DateTimeFormatter.ofPattern("HH:MM")

    fun setUpdateEmotion(
        emotionPosition: Int,
        emotionList: List<ResponsePostDetailEmotionData.Data>,
        userImage: Int,
    ) {
        val emotionCopyList = emotionList.map {
            it.copy()
        } as MutableList<ResponsePostDetailEmotionData.Data>

        if (emotionCopyList.contains(oldEmotionData)) {
            val index = emotionCopyList.indexOf(oldEmotionData)

            if (emotionCopyList[index].emotion == popupWindowImage[emotionPosition]) {
                setOldEmotionData(0, 0)
                emotionCopyList.removeAt(index)
            } else {
                setOldEmotionData(emotionPosition, userImage)
                emotionCopyList[index].emotion = popupWindowImage[emotionPosition]
            }

        } else {
            when (emotionPosition) {
                PopupWindowType.Type1.emotionPosition -> {
                    emotionCopyList.add(
                        ResponsePostDetailEmotionData.Data(
                            userImage,
                            PopupWindowType.Type1.drawable
                        )
                    )
                }
                PopupWindowType.Type2.emotionPosition -> {
                    emotionCopyList.add(
                        ResponsePostDetailEmotionData.Data(
                            userImage,
                            PopupWindowType.Type2.drawable
                        )
                    )
                }
                PopupWindowType.Type3.emotionPosition -> {
                    emotionCopyList.add(
                        ResponsePostDetailEmotionData.Data(
                            userImage,
                            PopupWindowType.Type3.drawable
                        )
                    )
                }
                PopupWindowType.Type4.emotionPosition -> {
                    emotionCopyList.add(
                        ResponsePostDetailEmotionData.Data(
                            userImage,
                            PopupWindowType.Type4.drawable
                        )
                    )
                }
                PopupWindowType.Type5.emotionPosition -> {
                    emotionCopyList.add(
                        ResponsePostDetailEmotionData.Data(
                            userImage,
                            PopupWindowType.Type5.drawable
                        )
                    )
                }
                PopupWindowType.Type6.emotionPosition -> {
                    emotionCopyList.add(
                        ResponsePostDetailEmotionData.Data(
                            userImage,
                            PopupWindowType.Type6.drawable
                        )
                    )
                }
            }
            setOldEmotionData(emotionPosition, userImage)
        }

        _isUpdateEmotion.value = emotionCopyList
    }

    fun setEmotionCount(count: Int) {
        _emotionCount.value = count
    }

    private fun setOldEmotionData(emotionPosition: Int, userImage: Int) {
        oldEmotionData = ResponsePostDetailEmotionData.Data(
            userImage,
            popupWindowImage[emotionPosition]
        )
    }

    fun setSelected() {
        _isSelected.value = _isSelected.value != true
    }

    fun setUpdateComment(
        commentList: List<ResponsePostDetailCommentData.Data>,
        sticker: Int
    ) {
        val commentCopyList = commentList.map {
            it.copy()
        } as MutableList<ResponsePostDetailCommentData.Data>

        if (sticker != 0) {
            // TODO 사용자 정보 등록 필요
            commentCopyList.add(
                ResponsePostDetailCommentData.Data(
                    R.drawable.img_no_group,
                    "User1",
                    sticker,
                    "",
                    (LocalDateTime.now()).format(formatter)
                )
            )
        } else {
            // TODO 스티커가 없을 때의 댓글 등록
        }
        _isUpdateComment.value = commentCopyList
    }
}