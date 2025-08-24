package com.uaeaa.sendmoneyapp.presentation.ui.requesthisotry

import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity

data class RequestHistoryUiState( val isLoading: Boolean = true,
                                  val requests: List<RequestHistoryEntity> = emptyList(),val error:String="")
