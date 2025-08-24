package com.uaeaa.sendmoneyapp.domain.usecases

import com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry.ISendMoneyReposiotry
import com.uaeaa.sendmoneyapp.domain.Field

class GetProviderFieldsImpl(val sendMoneyReposiotry: ISendMoneyReposiotry) : IGetProviderFields {
    override suspend fun getFromFileds(
        lan: String,
        serviceBName: String,
        providerId: String
    ): List<Field> {
        return sendMoneyReposiotry.getFormFields(lan, serviceBName, providerId)
    }


}