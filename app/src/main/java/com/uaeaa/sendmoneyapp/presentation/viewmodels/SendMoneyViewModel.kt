package com.uaeaa.sendmoneyapp.presentation.viewmodels

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import com.uaeaa.sendmoneyapp.domain.Lang
import com.uaeaa.sendmoneyapp.domain.usecases.IGetProviderFields
import com.uaeaa.sendmoneyapp.domain.usecases.IGetProviders
import com.uaeaa.sendmoneyapp.domain.usecases.IGetServiceUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.ISendMoneyUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.IValidateFormUseCase
import com.uaeaa.sendmoneyapp.presentation.toUIFormFields
import com.uaeaa.sendmoneyapp.presentation.ui.sendmoney.DropdownItem
import com.uaeaa.sendmoneyapp.presentation.ui.sendmoney.SendMoneyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(
    val iGetServiceUseCase: IGetServiceUseCase,
    val iGetProviders: IGetProviders,
    val iGetProviderFields: IGetProviderFields,
    val iValidateFormUseCase: IValidateFormUseCase,
    val iSendMoneyUseCase: ISendMoneyUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SendMoneyState())
    val state: StateFlow<SendMoneyState> = _state

    init {
        viewModelScope.launch {
            val services = iGetServiceUseCase.getServices(_state.value.lang.name.toLowerCase())
            _state.update {
                it.copy(services = services.map { service ->
                    DropdownItem(
                        id = service.name,
                        label = service.label // already localized
                    )
                })
            }

        }
    }

    fun onServiceSelected(serviceName: String) = viewModelScope.launch {
if(serviceName==_state.value.selectedService){
    return@launch
}
        val providers = iGetProviders.getProviders(_state.value.lang.name.toLowerCase(), serviceName)
        Log.d("letsodit", "onServiceSelected: ${providers.size}")
        _state.update {
            it.copy(
                selectedService = serviceName,
                providers = providers.map { provider ->
                    DropdownItem(
                        id = provider.id,
                        label = provider.name // already localized
                    )
                },
                selectedProviderId = null,
                schema = emptyList(),
                values = emptyMap(),
                errors = emptyMap()
            )
        }
    }

    fun onProviderSelected(providerID: String) = viewModelScope.launch {
        Log.d("lanis", "onProviderSelected:  ${_state.value.lang.name.toLowerCase()}")
        if(providerID==_state.value.selectedProviderId){
            return@launch
        }
        _state.update {
            it.copy(
                selectedService = _state.value.selectedService,
                providers = _state.value.providers,
                selectedProviderId = providerID,
                schema = iGetProviderFields.getFromFileds(_state.value.lang.name.toLowerCase(), _state.value.selectedService!!, providerID)
                    .toUIFormFields(),
                values = emptyMap(),
                errors = emptyMap()
            )
        }
    }

    fun onFieldChanged(name: String, value: String) =viewModelScope.launch {
        // live per-field validation
        val s = _state.value
        val newValues = s.values.toMutableMap().apply { this[name] = value }
        val singleFieldResult = iValidateFormUseCase.ValidateFromFileds(s.schema.filter { it.name == name }, mapOf(name to value))
        val newErrors = s.errors.toMutableMap().apply { this[name] = singleFieldResult.errors[name] }
        _state.update { it.copy(values = newValues, errors = newErrors) }
    }

    fun onSubmit() = viewModelScope.launch{
        val s = _state.value
        val result = iValidateFormUseCase.ValidateFromFileds(s.schema, s.values)
        if (!result.isValid) {
            _state.update { it.copy(errors = result.errors) }
            return@launch
        }
        val requestEntity = RequestHistoryEntity(
            serviceName = s.services.find { it.id == s.selectedService }?.label.orEmpty(),
            providerId = s.selectedProviderId.orEmpty(),
            providerName = s.providers.find { it.id == s.selectedProviderId }?.label.orEmpty(),
            amount = s.values["amount"].orEmpty(), // depends on your schema key
            dataJson = Gson().toJson(s.values) // or kotlinx.serialization
        )
        iSendMoneyUseCase.sendMoneyUseCase(requestEntity)
        _state.update { it.copy(toast = if (s.lang == Lang.AR) "تم الحفظ" else "Saved") }
    }

    fun onToastShown() = _state.update { it.copy(toast = null) }

    fun onLanguageChange(newLang: Lang)=viewModelScope.launch {
        val s = _state.value
        _state.update { it.copy(lang = newLang) }
        if (s.selectedService != null && s.selectedProviderId != null) {
            _state.update { it.copy(schema = iGetProviderFields.getFromFileds(newLang.name.toLowerCase(), _state.value.selectedService!!,
                _state.value.selectedProviderId!!
            )
                .toUIFormFields()) }
        }
    }
}
