package com.dnd_8th_4_android.wery.data.remote.model.write

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse

data class ResponseGroupList(
    val id: Long,
    val groupName: String,
    val groupImg: String
) : BaseResponse()