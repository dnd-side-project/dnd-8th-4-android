package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.remote.model.search.ResponsePostSearchData

interface SearchDataSource {

    suspend fun groupPostSearch(
        groupId: String,
        word: String,
        page: Int,
    ): ResponsePostSearchData
}