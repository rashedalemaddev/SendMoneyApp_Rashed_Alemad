package com.uaeaa.sendmoneyapp.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uaeaa.sendmoneyapp.domain.Service
import com.uaeaa.sendmoneyapp.domain.usecases.IGetServiceUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.ILoginUseCase
import com.uaeaa.sendmoneyapp.domain.usecases.IValidateLoginUseCase
import com.uaeaa.sendmoneyapp.presentation.ui.login.LoginFormState
import com.uaeaa.sendmoneyapp.presentation.ui.login.LoginState
import com.uaeaa.sendmoneyapp.presentation.ui.sendmoney.DropdownItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val iValidateLoginUseCase: IValidateLoginUseCase,
    val iLoginUseCase: ILoginUseCase
) : ViewModel() {
    var _loginState = MutableStateFlow<LoginState>(LoginState.Idle);
    val loginState: StateFlow<LoginState> = _loginState;


    private var _loginFormState = MutableStateFlow(LoginFormState())
    val loginFormState: StateFlow<LoginFormState> = _loginFormState


    val isFormValid: StateFlow<Boolean> = loginFormState
        .map { state ->
            _loginFormState.value.userName.isNotBlank() &&
                    _loginFormState.value.password.isNotBlank() &&
                    _loginFormState.value.userNameError.isBlank() &&
                    _loginFormState.value.passwordError.isBlank()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    fun onUserNameChanged(newUserName: String) {
        val error = iValidateLoginUseCase.validate(newUserName, loginFormState.value.password)
        _loginFormState.update {
            it.copy(
                userName = newUserName,
                userNameError = error.usernameError ?: ""
            )
        }
    }

    fun onPasswordChanged(newPassword: String) {
        val validationResult =
            iValidateLoginUseCase.validate(loginFormState.value.userName, newPassword)
        _loginFormState.update {
            it.copy(
                password = newPassword,
                passwordError = validationResult.passwordError ?: ""
            )
        }
    }

    fun onLoginClicked() = viewModelScope.launch {
        _loginState.value = LoginState.Loading
        delay(2000)
        if (isFormValid.value) {


            iLoginUseCase.login(
                loginFormState.value.userName,
                loginFormState.value.password
            ).fold(
                ifLeft = { error ->
                    _loginState.value = LoginState.Error(error ?: "Unknown error")
                },
                ifRight = {
                    _loginState.value = LoginState.Success
                }
            )
        }
    }

}


