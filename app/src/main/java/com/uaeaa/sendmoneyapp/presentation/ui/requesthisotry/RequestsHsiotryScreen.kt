package com.uaeaa.sendmoneyapp.presentation.ui.requesthisotry
import android.net.Uri

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.gson.GsonBuilder
import com.uaeaa.sendmoneyapp.data.models.RequestHistoryEntity
import com.uaeaa.sendmoneyapp.presentation.ui.CustomTopBar
import com.uaeaa.sendmoneyapp.presentation.viewmodels.RequestsHistoryViewNodel

@Composable
fun RequestsHsiotryScreen(
    navController: NavHostController,
    viewModel: RequestsHistoryViewNodel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()


    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Requests History ",
                backgroundColor = MaterialTheme.colorScheme.primary,
                actions = {
            }, onBackClick = {navController.popBackStack()}, showBackButton = true



            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.requests.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No requests found")
                }
            }
            !uiState.error.isNullOrBlank() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("something went wrong")
                }
            }

            else -> {
                LazyColumn(contentPadding = padding) {
                    items(items = uiState.requests) { request ->
                        RequestCard(
                            request = request,
                            onViewJsonClick = {

                                navController.navigate("request_details/${request.dataJson}") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RequestCard(
    request: RequestHistoryEntity,
    onViewJsonClick:  (RequestHistoryEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
      //  elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Row with Request ID and Amount
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "ID: ${request.requestId}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Amount: ${request.amount}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row with Service Name and Provider
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Service: ${request.serviceName}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Provider: ${request.providerName}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // JSON button
            TextButton(
                onClick = { onViewJsonClick(request) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("View JSON")
            }
        }
    }
}


