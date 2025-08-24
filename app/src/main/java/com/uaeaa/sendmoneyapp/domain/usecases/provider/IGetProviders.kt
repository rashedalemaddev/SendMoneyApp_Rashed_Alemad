package com.uaeaa.sendmoneyapp.domain.usecases.provider

import arrow.core.Either
import com.uaeaa.sendmoneyapp.domain.models.Failure
import com.uaeaa.sendmoneyapp.domain.models.Provider

interface IGetProviders {
    suspend   fun getProviders(lan :String,serviceBName:String): Either<Failure, List<Provider>>

}