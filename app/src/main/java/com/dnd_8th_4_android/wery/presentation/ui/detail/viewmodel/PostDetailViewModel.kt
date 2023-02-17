package com.dnd_8th_4_android.wery.presentation.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dnd_8th_4_android.wery.data.remote.model.detail.ResponsePostDetailEmotionData
import com.dnd_8th_4_android.wery.domain.model.PopupWindowType

class PostDetailViewModel : ViewModel() {
    private val _isUpdateList = MutableLiveData<MutableList<ResponsePostDetailEmotionData.Data>>()
    val isUpdateList: LiveData<MutableList<ResponsePostDetailEmotionData.Data>> = _isUpdateList

    fun setUpdateList(
        emotionPosition: Int,
        emotionList: List<ResponsePostDetailEmotionData.Data>,
        userImage: Int,
    ) {
        val emotionCopyList = emotionList.map {
            it.copy()
        } as MutableList<ResponsePostDetailEmotionData.Data>

        when (emotionPosition) {
            PopupWindowType.Type1.emotionPosition -> {
                emotionCopyList.add(
                    ResponsePostDetailEmotionData.Data(
                        Pair(
                            userImage,
                            PopupWindowType.Type1.drawable
                        )
                    )
                )
            }
            PopupWindowType.Type2.emotionPosition -> {
                emotionCopyList.add(
                    ResponsePostDetailEmotionData.Data(
                        Pair(
                            userImage,
                            PopupWindowType.Type2.drawable
                        )
                    )
                )
            }
            PopupWindowType.Type3.emotionPosition -> {
                emotionCopyList.add(
                    ResponsePostDetailEmotionData.Data(
                        Pair(
                            userImage,
                            PopupWindowType.Type3.drawable
                        )
                    )
                )
            }
            PopupWindowType.Type4.emotionPosition -> {
                emotionCopyList.add(
                    ResponsePostDetailEmotionData.Data(
                        Pair(
                            userImage,
                            PopupWindowType.Type4.drawable
                        )
                    )
                )
            }
            PopupWindowType.Type5.emotionPosition -> {
                emotionCopyList.add(
                    ResponsePostDetailEmotionData.Data(
                        Pair(
                            userImage,
                            PopupWindowType.Type5.drawable
                        )
                    )
                )
            }
            PopupWindowType.Type6.emotionPosition -> {
                emotionCopyList.add(
                    ResponsePostDetailEmotionData.Data(
                        Pair(
                            userImage,
                            PopupWindowType.Type6.drawable
                        )
                    )
                )
            }
        }
        _isUpdateList.value = emotionCopyList
    }
}