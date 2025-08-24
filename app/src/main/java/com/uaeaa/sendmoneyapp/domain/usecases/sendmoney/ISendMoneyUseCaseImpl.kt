package com.uaeaa.sendmoneyapp.domain.usecases.sendmoney

import arrow.core.Either
import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry.ISendMoneyReposiotry
import com.uaeaa.sendmoneyapp.domain.models.Failure

class ISendMoneyUseCaseImpl (val sendReposiotry: ISendMoneyReposiotry): ISendMoneyUseCase {
    override suspend fun sendMoneyUseCase(requestHistoryEntity: RequestHistoryEntity) : Either<Failure, Unit> {
       return sendReposiotry.sendRequest(requestHistoryEntity)
    }
}