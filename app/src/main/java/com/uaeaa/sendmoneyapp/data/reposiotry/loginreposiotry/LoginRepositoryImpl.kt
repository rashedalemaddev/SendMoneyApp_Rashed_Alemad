package com.uaeaa.sendmoneyapp.data.reposiotry.loginreposiotry

import arrow.core.Either
import arrow.core.left
import arrow.core.right

class LoginRepositoryImpl : ILoginRepsoiotry {
    override suspend fun login(userName: String, password: String): Either<String, String> {
        if (userName.toLowerCase().trim() == "testuser" && password.trim().toLowerCase() == "password123") {
            return "Success".right()
        }
        return "Invalid UserName or Password".left()
    }
}