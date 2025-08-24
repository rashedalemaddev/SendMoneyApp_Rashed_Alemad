package com.uaeaa.sendmoneyapp.presentation.ui


import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect

import androidx.compose.ui.graphics.Color

import com.google.accompanist.systemuicontroller.rememberSystemUiController
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    val systemUiController = rememberSystemUiController()

    // set status bar color same as toolbar
    SideEffect {
        systemUiController.setStatusBarColor(color = backgroundColor)
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                color = contentColor,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = contentColor
                    )
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = contentColor
        )
    )
}
