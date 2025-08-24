package com.uaeaa.sendmoneyapp.data.reposiotry.requestshistory

import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import kotlinx.coroutines.flow.Flow

interface RequestHistoryReposiotry {

    suspend fun getRequestHistory(): Flow<List<RequestHistoryEntity>>
}