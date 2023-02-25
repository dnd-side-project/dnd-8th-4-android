package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.ResponsePopupBottomData

interface PopupBottomRepository {

    suspend fun setBookmark(
        contentId: Int,
    ): ResponsePopupBottomData

    suspend fun setPostDelete(
        contentId: Int,
    ): BaseResponse
}