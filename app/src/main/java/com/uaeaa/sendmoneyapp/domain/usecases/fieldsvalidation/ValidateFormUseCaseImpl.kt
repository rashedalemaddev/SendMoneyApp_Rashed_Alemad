package com.uaeaa.sendmoneyapp.domain.usecases.fieldsvalidation

import com.uaeaa.sendmoneyapp.domain.models.UIFormField

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
            return if (v.isBlank()) "${f.validationErrorMessage}" else null
        }
        val ok = runCatching { Regex(f.regex).matches(v) }.getOrDefault(false)

        return if (!ok)  if(!f.validationErrorMessage.isNullOrBlank()){ f.validationErrorMessage}else {"${f.label} is invalid"} else null
    }

    private fun validateNumber(f: UIFormField.Number, v: String): String? {
        if (v.isBlank()) return "${f.validationErrorMessage}"
        if (v.toBigDecimalOrNull() == null) return if(!f.validationErrorMessage.isNullOrBlank()){ f.validationErrorMessage}else {"${f.label} must be numeric"}
        if (!f.regex.isNullOrBlank()) {
            val ok = runCatching { Regex(f.regex).matches(v) }.getOrDefault(false)
            if (!ok) return if(!f.validationErrorMessage.isNullOrBlank()){ f.validationErrorMessage}else {"${f.label} is invalid"}
        }
        return null
    }

    private fun validateOption(f: UIFormField.Option, v: String): String? {
        if (v.isBlank()) return if(!f.validationErrorMessage.isNullOrBlank()){ f.validationErrorMessage}else {"${f.label} is required"}
        val allowed = f.options.any { it.second == v }
        return if (!allowed) "${f.label} has invalid value" else null
    }
}
