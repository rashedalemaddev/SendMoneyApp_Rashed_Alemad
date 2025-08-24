package com.uaeaa.sendmoneyapp.presentation.ui.requestjsondetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.uaeaa.sendmoneyapp.presentation.ui.CustomTopBar

@Composable
fun RequestDetailsScreen(requestJson: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Request Details" ,
                showBackButton = true,
                onBackClick = onBack


            )
        }
    ) { padding->
        Box(
            modifier = Modifier.padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = requestJson,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
