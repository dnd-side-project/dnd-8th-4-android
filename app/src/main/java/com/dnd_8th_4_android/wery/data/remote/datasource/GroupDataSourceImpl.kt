package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.GroupService
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupInformationData
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseGroupMissionData
import com.dnd_8th_4_android.wery.data.remote.model.group.ResponseSetBookmarkData
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import javax.inject.Inject

class GroupDataSourceImpl @Inject constructor(private val groupService: GroupService) :
    GroupDataSource {

    override suspend fun getBookmarkList(): ResponseBookmarkData {
        return groupService.getBookmarkList()
    }

    override suspend fun signGroup(): ResponseGroupData {
        return groupService.signGroup()
    }

    override suspend fun setBookmark(groupId: Int): ResponseSetBookmarkData {
        return groupService.setBookmark(groupId)
    }

    override suspend fun allGroupPost(groupId: Int, page: Int): ResponsePostData {
        return groupService.allGroupPost(groupId, page)
    }

    override suspend fun sendEmotionData(contentId: Int, body: RequestEmotionStatus): ResponseEmotionData {
        return groupService.sendEmotion(contentId, body)
    }

    override suspend fun getMission(groupId: Int): ResponseGroupMissionData {
        return groupService.getMission(groupId)
    }

    override suspend fun getGroupInformation(groupId: Int): ResponseGroupInformationData {
        return groupService.getGroupInformation(groupId)
    }
}