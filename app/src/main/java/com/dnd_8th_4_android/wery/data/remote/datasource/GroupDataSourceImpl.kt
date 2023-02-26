package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.GroupService
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import javax.inject.Inject

class GroupDataSourceImpl @Inject constructor(private val groupService: GroupService) :
    GroupDataSource {

    override suspend fun getBookmarkList(): ResponseBookmarkData {
        return groupService.getBookmarkList()
    }

    override suspend fun signGroup(): ResponseGroupData {
        return groupService.signGroup()
    }
}