package com.uaeaa.sendmoneyapp.domain.usecases

import com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry.ISendMoneyReposiotry
import com.uaeaa.sendmoneyapp.domain.Provider

class GetProvidersImpl (val sendMoneyReposiotry: ISendMoneyReposiotry) :IGetProviders {
    override suspend fun getProviders(lan: String, serviceBName: String): List<Provider> {
        return   sendMoneyReposiotry.getProviders(lan,serviceBName)
    }
}