package com.dnd_8th_4_android.wery.data.remote.model.group

data class RequestGroupInviteData(
    val groupId: Int,
    val invitedUserIdList: List<Int>,
)