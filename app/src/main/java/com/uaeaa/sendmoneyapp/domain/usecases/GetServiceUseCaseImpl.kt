package com.uaeaa.sendmoneyapp.domain.usecases

import com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry.ISendMoneyReposiotry
import com.uaeaa.sendmoneyapp.domain.Service

class GetServiceUseCaseImpl(val sendMoneyReposiotry: ISendMoneyReposiotry) : IGetServiceUseCase {

    override suspend fun getServices(lan: String): List<Service> {
        return sendMoneyReposiotry.getServices(lan)
    }




}