package com.uaeaa.sendmoneyapp.presentation.ui.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.uaeaa.sendmoneyapp.presentation.viewmodels.LoginViewModel
import com.uaeaa.sendmoneyapp.ui.theme.AppTextFieldColors
@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val state by viewModel.loginState.collectAsState()
    val loginFormState by viewModel.loginFormState.collectAsState()
    val isFormValid by viewModel.isFormValid.collectAsState()
    var context=   LocalContext.current

    val scrollState = rememberScrollState()
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
    LaunchedEffect(state) {
        if (state is LoginState.Error) {
            Toast.makeText(context, (state as LoginState.Error)?.errorMessage, Toast.LENGTH_SHORT).show()
        }
        if (state is LoginState.Success) {
            navController.navigate("sendMoneyScreen") {
                popUpTo("login") { inclusive = true } // removes login from backstack
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .imePadding()             // allows scrolling when keyboard opens
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(screenHeightDp * 0.10f))

        Text(
            text = "Send Money App",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Welcome to Send Money App!",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(screenHeightDp * 0.20f)) // pushes fields toward center

        OutlinedTextField(
            isError = loginFormState.userNameError.isNotEmpty(),
            value = loginFormState.userName,
            onValueChange = { viewModel.onUserNameChanged(it) },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            colors = AppTextFieldColors
        )
        if (loginFormState.userNameError.isNotEmpty()) {
            Text(
                text = loginFormState.userNameError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            isError = loginFormState.passwordError.isNotEmpty(),
            value = loginFormState.password,
            onValueChange = { viewModel.onPasswordChanged(it) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            colors = AppTextFieldColors
        )
        if (loginFormState.passwordError.isNotEmpty()) {
            Text(
                text = loginFormState.passwordError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            enabled = isFormValid,
            onClick = { viewModel.onLoginClicked() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (state is LoginState.Loading) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text("Logging in...")
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp)
                    )
                }
            } else {
                Text("Login")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "By proceeding you agree to terms and Privacy Policy!",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
