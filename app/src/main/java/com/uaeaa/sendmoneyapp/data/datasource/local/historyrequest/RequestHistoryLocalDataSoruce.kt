package com.uaeaa.sendmoneyapp.data.datasource.local.historyrequest

import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import kotlinx.coroutines.flow.Flow

interface RequestHistoryLocalDataSoruce {
    suspend fun insertRequest(request: RequestHistoryEntity)
    suspend fun getAllRequests(): Flow<List<RequestHistoryEntity>>

}