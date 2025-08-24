package com.uaeaa.sendmoneyapp.domain.usecases.service

import arrow.core.Either
import com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry.ISendMoneyReposiotry
import com.uaeaa.sendmoneyapp.domain.models.Failure
import com.uaeaa.sendmoneyapp.domain.models.Service

class GetServiceUseCaseImpl(val sendMoneyReposiotry: ISendMoneyReposiotry) : IGetServiceUseCase {

    override suspend fun getServices(lan: String): Either<Failure, List<Service>> {
        return sendMoneyReposiotry.getServices(lan)
    }




}