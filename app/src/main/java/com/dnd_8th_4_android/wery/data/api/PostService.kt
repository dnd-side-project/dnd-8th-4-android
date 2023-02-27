package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponseGroupList
import com.dnd_8th_4_android.wery.data.remote.model.post.ResponsePostData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface PostService {

    @GET("/group/list/my")
    suspend fun getMyGroupList(): Response<ResponseGroupList>

    @Multipart
    @POST("/content")
    suspend fun postFeed(
        @Query("groupId") groupId: Long,
        @PartMap data: HashMap<String, RequestBody>,
        @Part multipartFile: MutableList<MultipartBody.Part>
    ): Response<BaseResponse>

    @GET("/content")
    suspend fun getPostData(
        @Query("contentId") contentId: Int,
    ):Response<ResponsePostData>

}