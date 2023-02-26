package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.PostDataSource
import com.dnd_8th_4_android.wery.data.remote.model.write.ResponseGroupList
import com.dnd_8th_4_android.wery.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val postDataSource: PostDataSource) :
    PostRepository {

    override suspend fun getMyGroupList(): Result<ResponseGroupList> {
        return postDataSource.getGroupList()
    }
}