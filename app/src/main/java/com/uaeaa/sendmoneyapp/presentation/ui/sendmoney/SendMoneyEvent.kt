package com.uaeaa.sendmoneyapp.presentation.ui.sendmoney

sealed class SendMoneyEvent {
    object Success : SendMoneyEvent() // submission succeeded
    data class Error(val message: String) : SendMoneyEvent() // submission failed
}