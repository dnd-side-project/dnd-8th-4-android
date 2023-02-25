package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.ResponsePopupBottomData

interface PopupBottomDataSource {

    suspend fun setBookmark(
        contentId: Int,
    ): ResponsePopupBottomData

    suspend fun setPostDelete(
        contentId: Int
    ): BaseResponse
}