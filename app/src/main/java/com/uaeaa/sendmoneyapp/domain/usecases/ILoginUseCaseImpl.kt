package com.uaeaa.sendmoneyapp.domain.usecases

import arrow.core.Either
import com.uaeaa.sendmoneyapp.data.reposiotry.loginreposiotry.ILoginRepsoiotry

class ILoginUseCaseImpl(val iLoginRepsoiotry: ILoginRepsoiotry) : ILoginUseCase {
    override suspend fun login(userName: String, password: String): Either<String, String> {
        return iLoginRepsoiotry.login(userName, password)
    }
}