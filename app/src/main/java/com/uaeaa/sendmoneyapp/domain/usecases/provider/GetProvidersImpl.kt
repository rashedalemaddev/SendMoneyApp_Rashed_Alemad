package com.uaeaa.sendmoneyapp.domain.usecases.provider

import arrow.core.Either
import com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry.ISendMoneyReposiotry
import com.uaeaa.sendmoneyapp.domain.models.Failure
import com.uaeaa.sendmoneyapp.domain.models.Provider

class GetProvidersImpl (val sendMoneyReposiotry: ISendMoneyReposiotry) : IGetProviders {
    override suspend fun getProviders(lan: String, serviceBName: String): Either<Failure, List<Provider>> {
        return   sendMoneyReposiotry.getProviders(lan,serviceBName)
    }
}