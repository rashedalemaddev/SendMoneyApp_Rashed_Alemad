package com.uaeaa.sendmoneyapp.presentation.ui.login

sealed class LoginState {
    object  Success : LoginState()
    object  Idle : LoginState()
    object  Loading : LoginState()
    data class Error (val errorMessage:String) : LoginState()


}