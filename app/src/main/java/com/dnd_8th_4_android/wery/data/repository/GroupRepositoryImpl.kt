package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.GroupDataSource
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseSetBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.domain.repository.GroupRepository
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(private val groupDataSource: GroupDataSource): GroupRepository {

    override suspend fun getBookmarkList(): ResponseBookmarkData {
        return groupDataSource.getBookmarkList()
    }

    override suspend fun signGroup(): ResponseGroupData {
        return groupDataSource.signGroup()
    }

    override suspend fun setBookmark(groupId: Int): ResponseSetBookmarkData {
        return groupDataSource.setBookmark(groupId)
    }
}