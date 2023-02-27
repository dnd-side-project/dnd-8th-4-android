package com.dnd_8th_4_android.wery.data.api

import com.dnd_8th_4_android.wery.data.remote.model.BaseResponse
import com.dnd_8th_4_android.wery.data.remote.model.group.*
import com.dnd_8th_4_android.wery.data.remote.model.home.RequestEmotionStatus
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseEmotionData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponseGroupData
import com.dnd_8th_4_android.wery.data.remote.model.home.ResponsePostData
import retrofit2.http.*

interface GroupService {

    @GET("group/list/star")
    suspend fun getBookmarkList(): ResponseBookmarkData

    @GET("/group/list")
    suspend fun signGroup(): ResponseGroupData

    @GET("/group/star")
    suspend fun setBookmark(
        @Query("groupId") groupId: Int,
    ): ResponseSetBookmarkData

    @GET("/content/group/all")
    suspend fun allGroupPost(
        @Query("groupId") groupId: Int,
        @Query("page") page: Int,
    ): ResponsePostData

    @POST("/content/{contentId}/emotion")
    suspend fun sendEmotion(
        @Path("contentId") contentId: Int,
        @Body body: RequestEmotionStatus,
    ): ResponseEmotionData

    @GET("/mission/list/group")
    suspend fun getMission(
        @Query("groupId") groupId: Int,
    ): ResponseGroupMissionData

    @GET("/group")
    suspend fun getGroupInformation(
        @Query("groupId") groupId: Int,
    ): ResponseGroupInformationData

    @DELETE("/group/delete")
    suspend fun deleteGroup(
        @Query("groupId") groupId: Int,
    ): BaseResponse

    @GET("/user/search")
    suspend fun getUserSearchList(
        @Query("keyword") keyword: String,
    ): ResponseUserSearchData
}