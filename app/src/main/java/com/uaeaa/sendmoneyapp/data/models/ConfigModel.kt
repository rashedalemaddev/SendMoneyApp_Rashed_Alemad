package com.uaeaa.sendmoneyapp.data.models

import android.util.Log
import com.uaeaa.sendmoneyapp.domain.models.Config
import com.uaeaa.sendmoneyapp.domain.models.Field
import com.uaeaa.sendmoneyapp.domain.models.FieldType
import com.uaeaa.sendmoneyapp.domain.models.Option
import com.uaeaa.sendmoneyapp.domain.models.Provider
import com.uaeaa.sendmoneyapp.domain.models.Service
import kotlinx.serialization.Serializable


@Serializable
data class ConfigDto(
    val services: List<ServiceDto>
)
// ServiceDto.kt
@Serializable
data class ServiceDto(
    val label: Map<String, String>,
    val name: String,
    val providers: List<ProviderDto>
)

// ProviderDto.kt
@Serializable
data class ProviderDto(
    val name: String,
    val id: String,
    val required_fields: List<FieldDto>
)

// FieldDto.kt
@Serializable
data class FieldDto(
    val label: Map<String, String>,
    val name: String,
    val placeholder: Placeholder?, // can be String or Map<String, String>
    val type: String,
    val validation: String?,
    val max_length: MaxLength?, // can be Int, String, or empty
    @Serializable(with = ValidationMessageSerializer::class)
    val validation_error_message: ValidationMessage? = null,
    val options: List<OptionDto>?=null
)
@Serializable
data class OptionDto(
    val label: String,
    val name: String
)

fun ConfigDto.toDomain(lang: String): Config =
    Config(services = services.map { it.toDomain(lang) })

fun ServiceDto.toDomain(lang: String): Service {
    return Service(
        name = name,
        label = label[lang] ?: label["en"].orEmpty(),
        providers = providers.map { it.toDomain(lang) }
    )
}

fun ProviderDto.toDomain(lang: String): Provider {
    return Provider(
        id = id,
        name = name,
        fields = emptyList()
    )
}

fun FieldDto.toDomain(lang: String): Field {

    val resolvedLabel = label[lang] ?: label["en"].orEmpty()

    Log.d("toDomain", "toDomain:  label is ${resolvedLabel}")


    val resolvedMaxLength = when (max_length) {
        is  MaxLength.StringLength -> max_length.value.toIntOrNull()
        is MaxLength.IntLength  -> max_length.value
        else -> null
    }
    val resolvedPlaceholder: String? = when (placeholder) {
        is Placeholder.Text -> placeholder.value
        is Placeholder.Localized -> placeholder.map[lang] ?: placeholder.map["en"]
        else -> null
    }
    val resolvedValidationMessage: String? = when (validation_error_message) {
        is ValidationMessage.Text -> validation_error_message.value
        is ValidationMessage.Localized -> validation_error_message.map[lang]
            ?: validation_error_message.map["en"]
        else -> null
    }
    val resolvedOptions = options?.map { Option(it.label, it.name) }
val type = when (type.lowercase()) {
    "number" -> FieldType.Number
    "option" -> FieldType.Option
    else -> FieldType.Text
}
    return Field(
        name = name,
        label = resolvedLabel,
        placeholder = resolvedPlaceholder,
        type =type  ,
        validation = validation,
        maxLength = resolvedMaxLength,
        validationErrorMessage = resolvedValidationMessage,
        options = resolvedOptions
    )
}

