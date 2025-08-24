package com.uaeaa.sendmoneyapp.data.reposiotry.sendmoneyreposiotry

import com.uaeaa.sendmoneyapp.data.ConfigDto
import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import com.uaeaa.sendmoneyapp.domain.Field
import com.uaeaa.sendmoneyapp.domain.Provider
import com.uaeaa.sendmoneyapp.domain.Service

interface ISendMoneyReposiotry {

    suspend fun loadConfig(): ConfigDto
    suspend fun getServices(lang:String):List<Service>
    suspend fun getProviders(lan:String,serviceName:String):List<Provider>

    suspend fun getFormFields(lang:String,serviceName:String,providerId:String):List<Field>

    suspend fun  sendRequest(requestHistoryEntity: RequestHistoryEntity)

}