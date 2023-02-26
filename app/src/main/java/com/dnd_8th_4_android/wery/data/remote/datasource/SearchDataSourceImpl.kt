package com.dnd_8th_4_android.wery.data.remote.datasource

import com.dnd_8th_4_android.wery.data.api.SearchService
import com.dnd_8th_4_android.wery.data.remote.model.search.ResponsePostSearchData
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(private val searchService: SearchService): SearchDataSource {

    override suspend fun groupPostSearch(
        groupId: String,
        word: String,
        page: Int,
    ): ResponsePostSearchData {
        return searchService.groupPostSearch(groupId, word, page)
    }
}