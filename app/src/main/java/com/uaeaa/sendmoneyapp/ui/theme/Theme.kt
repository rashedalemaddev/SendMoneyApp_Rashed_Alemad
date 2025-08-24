package com.uaeaa.sendmoneyapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    background = BackgroundLight,
    surface = InputBackground,
    onSurface = TextPrimary,
    secondary = TextSecondary,

)

private val DarkColors = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    background = BackgroundDark,
    surface = BackgroundDark,
    onSurface = TextPrimaryDark,
    secondary = TextSecondaryDark,
)
val AppTextFieldColors
    @Composable
    get() = OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = InputBorder, // or your custom border color
        focusedBorderColor = InputBorder,
        errorBorderColor = Color.Red,
        disabledBorderColor = Color.Gray,
        focusedContainerColor = LightColors.surface,
        unfocusedContainerColor = LightColors.surface,
        disabledContainerColor = LightColors.surface,
        errorContainerColor = LightColors.errorContainer
    )
@Composable
fun SendMoneyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}