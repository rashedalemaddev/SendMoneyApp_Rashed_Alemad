package com.uaeaa.sendmoneyapp.domain.usecases

interface IValidateLoginUseCase {
    data class LoginValidationResult(
        val usernameError: String? = null,
        val passwordError: String? = null,
        val isValid: Boolean = false
    )
    fun validate(username: String?, password: String?): LoginValidationResult
}