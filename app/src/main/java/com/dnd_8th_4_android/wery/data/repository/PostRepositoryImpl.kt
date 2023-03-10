package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.PostDataSource
import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.mission.ResponseMissionFeed
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseGroupList
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponsePostData
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
        data: HashMap<String, RequestBody>,
        multipartFile: MutableList<MultipartBody.Part>
    ): Result<BaseResponse> {
        return postDataSource.uploadFeed(groupId, data, multipartFile)
    }

    override suspend fun getPostData(contentId: Int): Result<ResponsePostData> {
        return postDataSource.getPostData(contentId)
    }

    override suspend fun modifyFeed(
        data: HashMap<String, RequestBody>,
        multipartFile: MutableList<MultipartBody.Part>
    ): Result<BaseResponse> {
        return postDataSource.modifyFeed(data,multipartFile)
    }

    override suspend fun uploadMissionFeed(
        data: HashMap<String, RequestBody>,
        multipartFiles: MutableList<MultipartBody.Part>?
    ): ResponseMissionFeed {
        return postDataSource.uploadMissionFeed(data, multipartFiles)
    }
}