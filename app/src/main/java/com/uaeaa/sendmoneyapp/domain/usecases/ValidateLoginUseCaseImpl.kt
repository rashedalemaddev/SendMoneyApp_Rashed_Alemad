package com.uaeaa.sendmoneyapp.domain.usecases


class ValidateLoginUseCaseImpl : IValidateLoginUseCase {
    override fun validate(
        username: String?,
        password: String?
    ): IValidateLoginUseCase.LoginValidationResult {
        val usernameError = if (username.isNullOrBlank()) "Username cannot be empty" else null
        val passwordError =
            if (password.isNullOrBlank() || password.length < 6) "Password must be at least 6 characters" else null

        return IValidateLoginUseCase.LoginValidationResult(
            usernameError = usernameError,
            passwordError = passwordError,
            isValid = usernameError == null && passwordError == null
        )
    }
}


