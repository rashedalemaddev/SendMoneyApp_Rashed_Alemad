package com.uaeaa.sendmoneyapp.domain.usecases.dynamicfileds

import arrow.core.Either
import com.uaeaa.sendmoneyapp.domain.models.Failure
import com.uaeaa.sendmoneyapp.domain.models.Field

interface IGetProviderFields {
    suspend   fun getFromFileds(lan :String,serviceBName:String,providerId:String):Either<Failure,List<Field>>

}