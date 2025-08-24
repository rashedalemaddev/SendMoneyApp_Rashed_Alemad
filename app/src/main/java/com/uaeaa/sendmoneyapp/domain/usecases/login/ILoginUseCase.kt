package com.uaeaa.sendmoneyapp.domain.usecases.login

import arrow.core.Either
import com.uaeaa.sendmoneyapp.domain.models.Failure

interface ILoginUseCase {
    suspend   fun login(userName:String,password:String): Either<Failure, String>
}