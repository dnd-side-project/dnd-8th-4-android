package com.dnd_8th_4_android.wery.data.remote.model.home

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class ResponseGroupData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val existGroup: Boolean,
        val groupInfoList: List<GroupInfo>
    ) {
        data class GroupInfo (
            @SerializedName("groupId") val id: Int,
            @SerializedName("groupImageUrl") val image: String,
            @SerializedName("groupName") val name: String,
        )
    }
}
