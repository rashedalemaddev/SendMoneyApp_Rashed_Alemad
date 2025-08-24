package com.uaeaa.sendmoneyapp.domain.usecases

import com.uaeaa.sendmoneyapp.domain.Field
import com.uaeaa.sendmoneyapp.domain.UIFormField

interface IValidateFormUseCase {
    data class Result(val isValid: Boolean, val errors: Map<String, String?>)
    suspend fun ValidateFromFileds(schema: List<UIFormField>, values: Map<String, String>): Result
}