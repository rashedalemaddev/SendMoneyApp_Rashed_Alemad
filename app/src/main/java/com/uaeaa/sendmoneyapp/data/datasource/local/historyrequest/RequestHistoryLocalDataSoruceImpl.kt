package com.uaeaa.sendmoneyapp.data.datasource.local.historyrequest

import com.uaeaa.sendmoneyapp.data.database.dao.RequestHistoryDao
import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import kotlinx.coroutines.flow.Flow

class RequestHistoryLocalDataSoruceImpl(val requestHistoryDao: RequestHistoryDao) :
    RequestHistoryLocalDataSoruce {
    override suspend fun insertRequest(request: RequestHistoryEntity) {
        requestHistoryDao.insertRequest(request)
    }

    override suspend fun getAllRequests(): Flow<List<RequestHistoryEntity>> {
        return requestHistoryDao.getAllRequests()
    }

}