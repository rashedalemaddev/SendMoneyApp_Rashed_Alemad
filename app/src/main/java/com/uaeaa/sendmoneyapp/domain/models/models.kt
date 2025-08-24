package com.uaeaa.sendmoneyapp.domain.models

// Domain models (pure Kotlin)
data class Config(
    val services: List<Service>
)
// Service.kt
data class Service(
    val name: String,
    val label: String, // already resolved by language
    val providers: List<Provider> = emptyList()
)

// Provider.kt
data class Provider(
    val id: String,
    val name: String,
    val fields: List<Field> = emptyList()
)

// Field.kt
data class Field(
    val name: String,
    val label: String,
    val placeholder: String?,
    val type: FieldType,
    val validation: String?,
    val maxLength: Int?,
    val validationErrorMessage: String?,
    val options: List<Option>?
)
data class Option(
    val label: String,
    val name: String
)
enum class FieldType { Text, Number, Option }
enum class Lang { EN, AR }
// UI-facing schema
sealed class UIFormField {
    abstract val name: String
    abstract val label: String
    abstract val placeholder: String?
   abstract val validationErrorMessage: String?

    data class Text(
        override val name: String,
        override val label: String,
        override val placeholder: String?,
        override val validationErrorMessage: String?,
        val regex: String?,
        val maxLength: Int?,
    ) : UIFormField()

    data class Number(
        override val name: String,
        override val label: String,
        override val placeholder: String?,
        override val validationErrorMessage: String?,
        val regex: String?,
        val maxLength: Int?
    ) : UIFormField()

    data class Option(
        override val name: String,
        override val label: String,
        override val placeholder: String?,
        override val validationErrorMessage: String?,
        val options: List<Pair<String/*display*/, String/*value*/>>,
        val regex: String?
    ) : UIFormField()
}


