package com.dnd_8th_4_android.wery.data.remote.model.group

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseGroupInformationData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val groupUserInfoList: MutableList<GroupUserInfoList>,
    ) {
        data class GroupUserInfoList(
            val groupName: String,
            val groupNote: String,
            val groupImageUrl: String,
            val groupCreatedAt: String,
            val memberCount: Int,
            val groupMemberInfoList: MutableList<GroupMemberInfoList>,
        ) {
            data class GroupMemberInfoList(
                val userName: String,
                val userProfileImageUrl: String,
            )
        }
    }
}

