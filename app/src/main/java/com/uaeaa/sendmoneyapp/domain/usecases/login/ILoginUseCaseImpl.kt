package com.uaeaa.sendmoneyapp.domain.usecases.login

import arrow.core.Either
import com.uaeaa.sendmoneyapp.data.reposiotry.loginreposiotry.ILoginRepsoiotry
import com.uaeaa.sendmoneyapp.domain.models.Failure

class ILoginUseCaseImpl(val iLoginRepsoiotry: ILoginRepsoiotry) : ILoginUseCase {
    override suspend fun login(userName: String, password: String): Either<Failure, String> {
        return iLoginRepsoiotry.login(userName, password)
    }
}