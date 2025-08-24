package com.uaeaa.sendmoneyapp.presentation

import com.uaeaa.sendmoneyapp.domain.Field
import com.uaeaa.sendmoneyapp.domain.FieldType
import com.uaeaa.sendmoneyapp.domain.UIFormField

fun List<Field>.toUIFormFields(): List<UIFormField> {
    return this.map { field ->
        when (field.type) {
            FieldType.Text -> UIFormField.Text(
                name = field.name,
                label = field.label,
                placeholder = field.placeholder,
                validationErrorMessage = field.validationErrorMessage,
                regex = field.validation,
                maxLength = field.maxLength
            )
            FieldType.Number -> UIFormField.Number(
                name = field.name,
                label = field.label,
                placeholder = field.placeholder,
                validationErrorMessage = field.validationErrorMessage,

                regex = field.validation,
                maxLength = field.maxLength
            )
            FieldType.Option -> UIFormField.Option(
                name = field.name,
                label = field.label,
                placeholder = field.placeholder,
                validationErrorMessage = field.validationErrorMessage,

                options = field.options?.map { it.label to it.name } ?: emptyList(),
                regex = field.validation
            )
        }
    }
}