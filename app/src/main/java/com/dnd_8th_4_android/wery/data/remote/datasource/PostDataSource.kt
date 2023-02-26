package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseGroupList
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PostDataSource {
    suspend fun getGroupList(): Result<ResponseGroupList>

    suspend fun uploadFeed(
        groupId: Long,
        requestBody: HashMap<String, RequestBody>,
        multipartFile: ArrayList<MultipartBody.Part>
    ): Result<BaseResponse>
}