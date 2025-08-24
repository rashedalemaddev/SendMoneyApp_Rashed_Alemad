package com.uaeaa.sendmoneyapp.domain.usecases

import arrow.core.Either

interface ILoginUseCase {
    suspend   fun login(userName:String,password:String): Either<String, String>
}