package com.dnd_8th_4_android.wery.data.remote.model.home

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.google.gson.annotations.SerializedName

data class ResponseGroupData(
    val data: Data,
) : BaseResponse() {
    data class Data(
        val existGroup: Boolean,
        val groupInfoList: MutableList<GroupInfo>
    ) {
        data class GroupInfo (
            @SerializedName("groupId") val id: Int,
            @SerializedName("groupName") val name: String,
            @SerializedName("groupImageUrl") val image: String,
            val groupNote: String,
            val memberCount: Int,
            val isStarGroup: Boolean,
        )
    }
}
