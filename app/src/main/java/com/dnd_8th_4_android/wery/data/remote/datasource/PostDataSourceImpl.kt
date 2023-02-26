package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.PostService
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseGroupList
import javax.inject.Inject

class PostDataSourceImpl @Inject constructor(private val postService: PostService): PostDataSource {

    override suspend fun getGroupList(): Result<ResponseGroupList> {
        val response = postService.getMyGroupList()
        if (response.isSuccessful) {
            response.body()?.let { return Result.success(it) }
        }
        return Result.failure(IllegalStateException(response.message()))
    }
}