package com.uaeaa.sendmoneyapp.domain.usecases.service

import arrow.core.Either
import com.uaeaa.sendmoneyapp.domain.models.Failure
import com.uaeaa.sendmoneyapp.domain.models.Service

interface IGetServiceUseCase {
    suspend   fun getServices(lan :String):Either<Failure, List<Service>>


}