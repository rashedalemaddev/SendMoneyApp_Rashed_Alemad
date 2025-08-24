package com.uaeaa.sendmoneyapp.domain.usecases

import com.uaeaa.sendmoneyapp.domain.UIFormField

class ValidateFormUseCaseImpl() : IValidateFormUseCase {
    override suspend fun ValidateFromFileds(
        schema: List<UIFormField>,
        values: Map<String, String>
    ): IValidateFormUseCase.Result {
        val errors = mutableMapOf<String, String?>()
        schema.forEach { f ->
            val v = values[f.name].orEmpty()
            val err = when (f) {
                is UIFormField.Text -> validateText(f, v)
                is UIFormField.Number -> validateNumber(f, v)
                is UIFormField.Option -> validateOption(f, v)
            }
            errors[f.name] = err
        }
        val valid = errors.values.none { it != null }
        return IValidateFormUseCase.Result(valid, errors)
    }

    private fun validateText(f: UIFormField.Text, v: String): String? {
        if (f.regex.isNullOrBlank()) {
            return if (v.isBlank()) "${f.label} is required" else null
        }
        val ok = runCatching { Regex(f.regex).matches(v) }.getOrDefault(false)

        return if (!ok) "${f.label} is invalid" else null
    }

    private fun validateNumber(f: UIFormField.Number, v: String): String? {
        if (v.isBlank()) return "${f.label} is required"
        if (v.toBigDecimalOrNull() == null) return "${f.label} must be numeric"
        if (!f.regex.isNullOrBlank()) {
            val ok = runCatching { Regex(f.regex).matches(v) }.getOrDefault(false)
            if (!ok) return "${f.label} is invalid"
        }
        return null
    }

    private fun validateOption(f: UIFormField.Option, v: String): String? {
        if (v.isBlank()) return "${f.label} is required"
        val allowed = f.options.any { it.second == v }
        return if (!allowed) "${f.label} has invalid value" else null
    }
}
