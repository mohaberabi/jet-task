package com.example.todo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = mdThemeLightPrimary,
    onPrimary = mdThemeLightOnPrimary,
    primaryContainer = mdThemeLightPrimaryContainer,
    onPrimaryContainer = mdThemeLightOnPrimaryContainer,
    secondary = mdThemeLightSecondary,
    onSecondary = mdThemeLightOnSecondary,
    secondaryContainer = mdThemeLightSecondaryContainer,
    onSecondaryContainer = mdThemeLightOnSecondaryContainer,
    tertiary = mdThemeLightTertiary,
    onTertiary = mdThemeLightOnTertiary,
    tertiaryContainer = mdThemeLightTertiaryContainer,
    onTertiaryContainer = mdThemeLightOnTertiaryContainer,
    error = mdThemeLightError,
    onError = mdThemeLightOnError,
    errorContainer = mdThemeLightErrorContainer,
    onErrorContainer = mdThemeLightOnErrorContainer,
    background = mdThemeLightBackground,
    onBackground = mdThemeLightOnBackground,
    surface = mdThemeLightSurface,
    onSurface = mdThemeLightOnSurface,
    surfaceVariant = mdThemeLightSurfaceVariant,
    onSurfaceVariant = mdThemeLightOnSurfaceVariant,
    outline = mdThemeLightOutline,
    inverseSurface = mdThemeLightInverseSurface,
    inverseOnSurface = mdThemeLightInverseOnSurface,
    inversePrimary = mdThemeLightInversePrimary,
    surfaceTint = mdThemeLightSurfaceTint,
)

private val DarkColors = darkColorScheme(
    primary = mdThemeDarkPrimary,
    onPrimary = mdThemeDarkOnPrimary,
    primaryContainer = mdThemeDarkPrimaryContainer,
    onPrimaryContainer = mdThemeDarkOnPrimaryContainer,
    secondary = mdThemeDarkSecondary,
    onSecondary = mdThemeDarkOnSecondary,
    secondaryContainer = mdThemeDarkSecondaryContainer,
    onSecondaryContainer = mdThemeDarkOnSecondaryContainer,
    tertiary = mdThemeDarkTertiary,
    onTertiary = mdThemeDarkOnTertiary,
    tertiaryContainer = mdThemeDarkTertiaryContainer,
    onTertiaryContainer = mdThemeDarkOnTertiaryContainer,
    error = mdThemeDarkError,
    onError = mdThemeDarkOnError,
    errorContainer = mdThemeDarkErrorContainer,
    onErrorContainer = mdThemeDarkOnErrorContainer,
    background = mdThemeDarkBackground,
    onBackground = mdThemeDarkOnBackground,
    surface = mdThemeDarkSurface,
    onSurface = mdThemeDarkOnSurface,
    surfaceVariant = mdThemeDarkSurfaceVariant,
    onSurfaceVariant = mdThemeDarkOnSurfaceVariant,
    outline = mdThemeDarkOutline,
    inverseSurface = mdThemeDarkInverseSurface,
    inverseOnSurface = mdThemeDarkInverseOnSurface,
    inversePrimary = mdThemeDarkInversePrimary,
    surfaceTint = mdThemeDarkSurfaceTint,
)

@Composable
fun TodoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (!darkTheme) {
        LightColors
    } else {
        DarkColors
    }

    MaterialTheme(
        colorScheme = colors,
        shapes = Shapes,
        typography = JetSurveyTypo,
        content = content,
    )
}