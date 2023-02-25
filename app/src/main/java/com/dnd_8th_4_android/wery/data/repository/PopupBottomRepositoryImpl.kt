package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.PopupBottomDataSource
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.ResponsePopupBottomData
import com.dnd_8th_4_android.wery.domain.repository.PopupBottomRepository
import javax.inject.Inject

class PopupBottomRepositoryImpl @Inject constructor(private val popupBottomDataSource: PopupBottomDataSource) :
    PopupBottomRepository {

    override suspend fun setBookmark(contentId: Int): ResponsePopupBottomData {
        return popupBottomDataSource.setBookmark(contentId)
    }

    override suspend fun setPostDelete(contentId: Int): BaseResponse {
        return popupBottomDataSource.setPostDelete(contentId)
    }
}