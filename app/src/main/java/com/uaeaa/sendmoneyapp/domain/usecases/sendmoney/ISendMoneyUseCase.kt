package com.uaeaa.sendmoneyapp.domain.usecases.sendmoney

import arrow.core.Either
import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import com.uaeaa.sendmoneyapp.domain.models.Failure

interface ISendMoneyUseCase {
    suspend fun sendMoneyUseCase(requestHistoryEntity: RequestHistoryEntity):Either<Failure,Unit>
}