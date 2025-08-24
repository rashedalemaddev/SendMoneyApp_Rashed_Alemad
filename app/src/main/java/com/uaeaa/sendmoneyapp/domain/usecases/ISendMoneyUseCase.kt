package com.uaeaa.sendmoneyapp.domain.usecases

import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity

interface ISendMoneyUseCase {
    suspend fun sendMoneyUseCase(requestHistoryEntity: RequestHistoryEntity)
}