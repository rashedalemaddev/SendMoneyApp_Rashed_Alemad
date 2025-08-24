package com.uaeaa.sendmoneyapp.domain.usecases.dynamicfileds

import arrow.core.Either
import com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry.ISendMoneyReposiotry
import com.uaeaa.sendmoneyapp.domain.models.Failure
import com.uaeaa.sendmoneyapp.domain.models.Field

class GetProviderFieldsImpl(val sendMoneyReposiotry: ISendMoneyReposiotry) : IGetProviderFields {
    override suspend fun getFromFileds(
        lan: String,
        serviceBName: String,
        providerId: String
    ): Either<Failure, List<Field>> {
        return sendMoneyReposiotry.getFormFields(lan, serviceBName, providerId)
    }


}