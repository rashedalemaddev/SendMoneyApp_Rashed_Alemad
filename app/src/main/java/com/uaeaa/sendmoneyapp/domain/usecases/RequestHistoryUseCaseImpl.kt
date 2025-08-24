package com.uaeaa.sendmoneyapp.domain.usecases

import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import com.uaeaa.sendmoneyapp.data.reposiotry.requestshistory.RequestHistoryReposiotry
import kotlinx.coroutines.flow.Flow

class RequestHistoryUseCaseImpl (val requestHistoryReposiotry: RequestHistoryReposiotry):IRequestHistoryUseCase{
    override suspend fun getRequestHistory(): Flow<List<RequestHistoryEntity>> {
        return  requestHistoryReposiotry.getRequestHistory()
    }
}