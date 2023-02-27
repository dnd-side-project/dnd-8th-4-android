package com.dnd_8th_4_android.wery.data.remote.model.write

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class ResponseGroupList(
    val data: List<ResultGroupList>
) : BaseResponse() {
    data class ResultGroupList(
        @SerializedName("groupId") val id: Long,
        @SerializedName("groupName") val groupName: String,
        @SerializedName("groupImageUrl") val groupImg: String
    )
}