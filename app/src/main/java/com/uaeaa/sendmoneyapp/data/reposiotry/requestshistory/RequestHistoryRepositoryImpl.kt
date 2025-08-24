package com.uaeaa.sendmoneyapp.data.reposiotry.requestshistory

import com.uaeaa.sendmoneyapp.data.datasource.local.historyrequest.RequestHistoryLocalDataSoruce
import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import kotlinx.coroutines.flow.Flow

class RequestHistoryRepositoryImpl(val requestHistoryLocalDataSoruce: RequestHistoryLocalDataSoruce) : RequestHistoryReposiotry {


    override suspend fun getRequestHistory(): Flow<List<RequestHistoryEntity>> {
       return requestHistoryLocalDataSoruce.getAllRequests()
    }
}