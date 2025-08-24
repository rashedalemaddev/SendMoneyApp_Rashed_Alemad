package com.uaeaa.sendmoneyapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uaeaa.sendmoneyapp.domain.usecases.history.IRequestHistoryUseCase
import com.uaeaa.sendmoneyapp.presentation.ui.requesthisotry.RequestHistoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestsHistoryViewNodel @Inject constructor(val iRequestHistoryUseCase: IRequestHistoryUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(RequestHistoryUiState())
    val uiState: StateFlow<RequestHistoryUiState> = _uiState.asStateFlow()

    init {

        loadRequestHistory()
    }

    private fun loadRequestHistory() {
        viewModelScope.launch {
            try {
                iRequestHistoryUseCase.getRequestHistory().collect { requests ->
                    _uiState.update { it.copy(requests = requests, isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unexpected error"
                    )
                }
            }
        }
    }

}