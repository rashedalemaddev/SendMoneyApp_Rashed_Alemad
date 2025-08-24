package com.uaeaa.sendmoneyapp.presentation.ui.sendmoney

import com.uaeaa.sendmoneyapp.domain.Lang

import com.uaeaa.sendmoneyapp.domain.UIFormField

data class SendMoneyState(
    val lang: Lang = Lang.EN,
    val services: List<DropdownItem> = emptyList(),
    val selectedService: String? = null,         // service machine name
    val providers: List<DropdownItem> = emptyList(),
    val selectedProviderId: String? = null,
    val schema: List<UIFormField> = emptyList(),
    val values: Map<String, String> = emptyMap(),
    val errors: Map<String, String?> = emptyMap(),
    val isSubmitting: Boolean = false,
    val toast: String? = null
)
