package com.dnd_8th_4_android.wery.domain.repository

import com.dnd_8th_4_android.wery.data.remote.model.search.ResponsePostSearchData

interface SearchRepository {

    suspend fun groupPostSearch(
        groupId: String,
        word: String,
        page: Int,
    ): ResponsePostSearchData
}