package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.PopupBottomService
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.ResponsePopupBottomData
import javax.inject.Inject

class PopupBottomDataSourceImpl @Inject constructor(private val popupBottomService: PopupBottomService) :
    PopupBottomDataSource {

    override suspend fun setBookmark(contentId: Int): ResponsePopupBottomData {
        return popupBottomService.setBookmark(contentId)
    }

    override suspend fun setPostDelete(contentId: Int): BaseResponse {
        return popupBottomService.setPostDelete(contentId)
    }
}