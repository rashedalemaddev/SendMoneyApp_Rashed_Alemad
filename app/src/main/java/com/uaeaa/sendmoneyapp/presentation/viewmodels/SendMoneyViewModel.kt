package com.uaeaa.sendmoneyapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import com.uaeaa.sendmoneyapp.domain.models.Lang
import com.uaeaa.sendmoneyapp.domain.usecases.dynamicfileds.IGetProviderFields
import com.uaeaa.sendmoneyapp.domain.usecases.provider.IGetProviders
import com.uaeaa.sendmoneyapp.domain.usecases.service.IGetServiceUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.sendmoney.ISendMoneyUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.fieldsvalidation.IValidateFormUseCase
import com.uaeaa.sendmoneyapp.presentation.toUIFormFields
import com.uaeaa.sendmoneyapp.presentation.ui.sendmoney.DropdownItem
import com.uaeaa.sendmoneyapp.presentation.ui.sendmoney.SendMoneyEvent
import com.uaeaa.sendmoneyapp.presentation.ui.sendmoney.SendMoneyState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private val _event = MutableSharedFlow<SendMoneyEvent>() // one-time events
    val event: SharedFlow<SendMoneyEvent> = _event


    init {
        viewModelScope.launch {
            val result = iGetServiceUseCase.getServices(_state.value.lang.name.toLowerCase())
            result.fold(
                ifLeft = { failure ->
                    // Update state with  toast error
                    _state.update { it.copy(toast = failure.message) }
                    _event.emit(SendMoneyEvent.Error(failure.message))

                },
                ifRight = { services ->
                    _state.update {
                        it.copy(
                            services = services.map { service ->
                                DropdownItem(id = service.name, label = service.label)
                            }
                        )
                    }
                }
            )

        }
    }

    fun onServiceSelected(serviceName: String) = viewModelScope.launch {
        if (serviceName == _state.value.selectedService) {
            return@launch
        }
        val result = iGetProviders.getProviders(_state.value.lang.name.toLowerCase(), serviceName)

        result.fold(
            ifLeft = { failure ->
                // Update state with  toast error
                _state.update { it.copy(toast = failure.message) }
                _event.emit(SendMoneyEvent.Error(failure.message))

            },
            ifRight = { provders ->
                _state.update {
                    it.copy(
                        selectedService = serviceName,
                        providers = provders.map { provider ->
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
        )

    }

    fun onProviderSelected(providerID: String) = viewModelScope.launch {
        Log.d("lanis", "onProviderSelected:  ${_state.value.lang.name.toLowerCase()}")
        if (providerID == _state.value.selectedProviderId) {
            return@launch
        }
        val result = iGetProviderFields.getFromFileds(
            _state.value.lang.name.toLowerCase(),
            _state.value.selectedService!!,
            providerID
        )

        result.fold(
            ifLeft = { failure ->
                // Update state with  toast error
                _state.update {
                    it.copy(toast = failure.message)

                }
                _event.emit(SendMoneyEvent.Error(failure.message))
            },
            ifRight = { dyniamicFileds ->
                _state.update {
                    it.copy(
                        selectedService = _state.value.selectedService,
                        providers = _state.value.providers,
                        selectedProviderId = providerID,
                        schema = dyniamicFileds
                            .toUIFormFields(),
                        values = emptyMap(),
                        errors = emptyMap()
                    )
                }
            }
        )


    }

    fun onFieldChanged(name: String, value: String) = viewModelScope.launch {
        // live per-field validation
        val s = _state.value
        val newValues = s.values.toMutableMap().apply { this[name] = value }
        val singleFieldResult = iValidateFormUseCase.ValidateFromFileds(
            s.schema.filter { it.name == name },
            mapOf(name to value)
        )
        val newErrors =
            s.errors.toMutableMap().apply { this[name] = singleFieldResult.errors[name] }
        _state.update { it.copy(values = newValues, errors = newErrors) }
    }

    fun onSubmit() = viewModelScope.launch {
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
            amount = s.values["amount"].orEmpty(),
            dataJson = Gson().toJson(s.values)
        )
        iSendMoneyUseCase.sendMoneyUseCase(requestEntity).fold(
            ifLeft = { faluire ->
                _state.update {
                    it.copy(toast = faluire.message)
                }
                _event.emit(SendMoneyEvent.Error(faluire.message))
            },
            ifRight = { _event.emit(SendMoneyEvent.Success) }
        )

        resetForm()


    }

    fun resetForm() {

        _state.update { current ->
            current.copy(
                values = emptyMap(), // clear user inputs
                errors = emptyMap()  // clear validation errors
            )

        }
    }


    fun onLanguageChange(newLang: Lang) = viewModelScope.launch {
        val cuurentState = _state.value
        _state.update { it.copy(lang = newLang) }
        if (cuurentState.selectedService != null && cuurentState.selectedProviderId != null) {
            val result = iGetProviderFields.getFromFileds(
                newLang.name.toLowerCase(),
                cuurentState.selectedService!!,
                cuurentState.selectedProviderId
            )

            result.fold(
                ifLeft = { failure ->
                    // Update state with  toast error
                    _state.update { it.copy(toast = failure.message) }
                },
                ifRight = { dyniamicFileds ->
                    _state.update {
                        it.copy(
                            selectedService = _state.value.selectedService,
                            providers = _state.value.providers,
                            selectedProviderId = cuurentState.selectedProviderId,
                            schema = dyniamicFileds
                                .toUIFormFields(),

                        )
                    }
                }
            )
        }
    }

}
