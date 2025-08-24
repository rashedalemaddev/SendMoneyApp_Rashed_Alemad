package com.uaeaa.sendmoneyapp.appnavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.uaeaa.sendmoneyapp.presentation.ui.login.LoginScreen
import com.uaeaa.sendmoneyapp.presentation.ui.requesthisotry.RequestsHsiotryScreen
import com.uaeaa.sendmoneyapp.presentation.ui.requestjsondetails.RequestDetailsScreen
import com.uaeaa.sendmoneyapp.presentation.ui.sendmoney.SendMoneyScreen


@Composable
fun AppNavigation(
    navController: NavHostController,

) {
    NavHost(navController, startDestination = "login") {

        composable("login") {
            LoginScreen(navController)
        }
        composable("sendMoneyScreen") {
            SendMoneyScreen(navController)
        }
        composable("requests_history") {
            RequestsHsiotryScreen(navController)
        }

        // ✅ New Request Details Screen
        composable(
            route = "request_details/{requestJson}",
        ) { backStackEntry ->
            val requestJson = backStackEntry.arguments?.getString("requestJson") ?: "{}"
            RequestDetailsScreen(
                requestJson = requestJson,
                onBack = { navController.popBackStack() }
            )
        }

    }
}
