package com.uaeaa.sendmoneyapp.presentation.ui.sendmoney

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.uaeaa.sendmoneyapp.domain.models.Lang
import com.uaeaa.sendmoneyapp.domain.models.UIFormField
import com.uaeaa.sendmoneyapp.presentation.ui.CustomTopBar
import com.uaeaa.sendmoneyapp.presentation.viewmodels.SendMoneyViewModel

@Composable
fun SendMoneyScreen(
    navController: NavHostController,
    viewModel: SendMoneyViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val rtl = state.lang == Lang.AR
    var context=   LocalContext.current

    val eventFlow = viewModel.event
    LaunchedEffect(key1 = true) {
        eventFlow.collect { event ->
            when (event) {
                SendMoneyEvent.Success -> {
                    navController.navigate("requests_history") {

                    }
                }

                is SendMoneyEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()

                    Log.e("SendMoney", event.message)
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier.imePadding(),

        topBar = {
            CustomTopBar(
                title = if (!rtl) "Send Money" else "ارسال الاموال",
                backgroundColor = MaterialTheme.colorScheme.primary,
                actions = {
                    // Language switch button
                    TextButton(onClick = {
                        val newLang = if (state.lang == Lang.EN) Lang.AR else Lang.EN
                        viewModel.onLanguageChange(newLang)
                    }) {
                        Text(
                            if (state.lang == Lang.EN) "عربي" else "EN",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    // View Requests History button
                    IconButton(onClick = {
                        navController.navigate("requests_history")
                    }) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "View Requests",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                }
            )
        }
    ) { padding ->


        CompositionLocalProvider(LocalLayoutDirection provides if (rtl) LayoutDirection.Rtl else LayoutDirection.Ltr) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .imePadding(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                        .imePadding()
                ) {


                    Spacer(Modifier.height(120.dp))

                    LabeledDropdown(
                        label = "Service",
                        items = state.services,
                        selected = state.selectedService,
                        onSelected = viewModel::onServiceSelected
                    )

                    Spacer(Modifier.height(16.dp))

                    if (state.selectedService != null) {

                        LabeledDropdown(
                            label = "Provider",
                            items = state.providers,
                            selected = state.selectedProviderId,
                            onSelected = viewModel::onProviderSelected
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    // Dynamic form
                    DynamicForm(
                        schema = state.schema,
                        values = state.values,
                        errors = state.errors,
                        onValueChange = viewModel::onFieldChanged
                    )
                    Spacer(modifier = Modifier.height(80.dp))


                }

                if (state.selectedService != null && state.selectedProviderId != null) {
                    Button(
                        onClick = { viewModel.onSubmit() },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(16.dp)
                            .imePadding(),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(if (!rtl) "Send" else "ارسال")
                    }

                }


            }
        }
    }

}


@Composable
private fun DynamicForm(
    schema: List<UIFormField>,
    values: Map<String, String>,
    errors: Map<String, String?>,
    onValueChange: (String, String) -> Unit
) {
    Column {
        schema.forEach { field ->
            when (field) {
                is UIFormField.Text -> FieldText(
                    field,
                    values[field.name].orEmpty(),
                    errors[field.name],
                    onValueChange
                )

                is UIFormField.Number -> FieldNumber(
                    field,
                    values[field.name].orEmpty(),
                    errors[field.name],
                    onValueChange
                )

                is UIFormField.Option -> FieldOption(
                    field,
                    values[field.name].orEmpty(),
                    errors[field.name],
                    onValueChange
                )
            }
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun FieldText(
    f: UIFormField.Text,
    value: String,
    error: String?,
    onValueChange: (String, String) -> Unit
) {


    Column(modifier = Modifier
        .fillMaxWidth()
       ) {
        // Label
        Spacer(Modifier.height(16.dp))
        Text(
            text = f.label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(

            value = value,
            onValueChange = {
                val clipped = if (f.maxLength != null) it.take(f.maxLength) else it
                onValueChange(f.name, clipped)
            },

            placeholder = {
                Text(
                    f.placeholder ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            isError = error != null,
            singleLine = true,
            modifier = Modifier

                .fillMaxWidth()
                , colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent, // remove border
                focusedBorderColor = Color.Transparent,   // remove border
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surface, // background
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                errorContainerColor = MaterialTheme.colorScheme.errorContainer
            )
        )
        if (error != null) Text(
            error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )

    }
}

@Composable
private fun FieldNumber(
    f: UIFormField.Number,
    value: String,
    error: String?,
    onValueChange: (String, String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(Modifier.height(16.dp))

        // Label
        Text(
            text = f.label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Spacer(Modifier.height(4.dp))

        OutlinedTextField(
            value = value,
            onValueChange = {
                val filtered = it.filter { ch -> ch.isDigit() || ch == '.' }
                val clipped =
                    if (f.maxLength != null && f.maxLength > 0) filtered.take(f.maxLength) else filtered
                onValueChange(f.name, clipped)
            },

            placeholder = {
                Text(
                    f.placeholder ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            isError = error != null,
            singleLine = true,
            modifier = Modifier

                .fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent, // remove border
                focusedBorderColor = Color.Transparent,   // remove border
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surface, // background
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                errorContainerColor = MaterialTheme.colorScheme.errorContainer
            )
        )
        if (error != null) Text(
            error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FieldOption(
    f: UIFormField.Option,
    value: String,
    error: String?,
    onValueChange: (String, String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedDisplay = f.options.firstOrNull { it.second == value }?.first.orEmpty()
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(Modifier.height(16.dp))

        // Label
        Text(
            text = f.label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Spacer(Modifier.height(4.dp))
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {


            OutlinedTextField(
                value = selectedDisplay,
                onValueChange = {},
                readOnly = true,

                placeholder = {
                    Text(
                        f.placeholder ?: "",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                textStyle = MaterialTheme.typography.titleMedium,

                isError = error != null,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier
                    .menuAnchor()

                    .fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    errorContainerColor = MaterialTheme.colorScheme.errorContainer
                )
            )
            ExposedDropdownMenu(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.surface),
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                f.options.forEach { (display, stored) ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                display,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = {
                            onValueChange(f.name, stored)
                            expanded = false
                        }
                    )
                }
            }
        }
        if (error != null) Text(
            error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LabeledDropdown(
    label: String,
    items: List<DropdownItem>,
    selected: String?,   // controlled from outside
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        // Label
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Spacer(Modifier.height(8.dp))

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {


            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    errorContainerColor = MaterialTheme.colorScheme.errorContainer
                ),
                textStyle = MaterialTheme.typography.titleMedium,
                readOnly = true,
                value = items.find { it.id == selected }?.label ?: "", // 🔹 derive value
                onValueChange = {},
                placeholder = { Text(label, style = MaterialTheme.typography.bodyMedium) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }


            )


            ExposedDropdownMenu(modifier = Modifier
                .background(MaterialTheme.colorScheme.surface),
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                item.label,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = {
                            expanded = false
                            onSelected(item.id)
                        }
                    )
                }
            }
        }
    }
}
