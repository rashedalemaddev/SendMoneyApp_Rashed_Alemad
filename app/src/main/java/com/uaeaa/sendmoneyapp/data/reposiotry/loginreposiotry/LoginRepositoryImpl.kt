package com.uaeaa.sendmoneyapp.data.reposiotry.loginreposiotry

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.uaeaa.sendmoneyapp.domain.models.Failure

class LoginRepositoryImpl : ILoginRepsoiotry {
    override suspend fun login(userName: String, password: String): Either<Failure, String> {
        if (userName.toLowerCase().trim() == "testuser" && password.trim().toLowerCase() == "password123") {
            return "Success".right()
        }
        return Failure("Invalid user name or password").left()
    }
}