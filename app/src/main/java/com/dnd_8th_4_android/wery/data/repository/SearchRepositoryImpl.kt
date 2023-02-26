package com.dnd_8th_4_android.wery.data.repository

import com.dnd_8th_4_android.wery.data.remote.datasource.SearchDataSource
import com.dnd_8th_4_android.wery.data.remote.model.search.ResponsePostSearchData
import com.dnd_8th_4_android.wery.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchDataSource: SearchDataSource) :
    SearchRepository {

    override suspend fun groupPostSearch(
        groupId: String,
        word: String,
        page: Int,
    ): ResponsePostSearchData {
        return searchDataSource.groupPostSearch(groupId, word, page)
    }
}