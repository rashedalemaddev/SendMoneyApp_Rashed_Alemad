package com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry

import arrow.core.Either
import com.uaeaa.sendmoneyapp.data.models.ConfigDto
import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import com.uaeaa.sendmoneyapp.domain.models.Failure
import com.uaeaa.sendmoneyapp.domain.models.Field
import com.uaeaa.sendmoneyapp.domain.models.Provider
import com.uaeaa.sendmoneyapp.domain.models.Service

interface ISendMoneyReposiotry {

    suspend fun loadConfig(): Either<Failure, ConfigDto>
    suspend fun getServices(lang:String): Either<Failure,List<Service>>
    suspend fun getProviders(lan:String,serviceName:String): Either<Failure,List<Provider>>

    suspend fun getFormFields(lang:String,serviceName:String,providerId:String): Either<Failure,List<Field>>

    suspend fun  sendRequest(requestHistoryEntity: RequestHistoryEntity): Either<Failure, Unit>

}