package com.uaeaa.sendmoneyapp.domain.usecases

import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import kotlinx.coroutines.flow.Flow

interface IRequestHistoryUseCase {
    suspend fun getRequestHistory(): Flow<List<RequestHistoryEntity>>
}