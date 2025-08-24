package com.uaeaa.sendmoneyapp.domain.usecases

import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry.ISendMoneyReposiotry

class ISendMoneyUseCaseImpl (val sendReposiotry: ISendMoneyReposiotry):ISendMoneyUseCase {
    override suspend fun sendMoneyUseCase(requestHistoryEntity: RequestHistoryEntity) {
        sendReposiotry.sendRequest(requestHistoryEntity)
    }
}