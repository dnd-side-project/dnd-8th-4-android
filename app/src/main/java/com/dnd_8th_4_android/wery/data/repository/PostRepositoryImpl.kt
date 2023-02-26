package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.PostDataSource
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseGroupList
import com.dnd_8th_4_android.wery.domain.repository.PostRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val postDataSource: PostDataSource) :
    PostRepository {

    override suspend fun getMyGroupList(): Result<ResponseGroupList> {
        return postDataSource.getGroupList()
    }

    override suspend fun uploadFeed(
        groupId: Long,
        requestBody: HashMap<String, RequestBody>,
        multipartFile: ArrayList<MultipartBody.Part>
    ): Result<BaseResponse> {
        return postDataSource.uploadFeed(groupId, requestBody, multipartFile)
    }
}