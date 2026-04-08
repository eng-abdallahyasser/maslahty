package com.abdallahyasser.maslahty.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = EgyptGold,
    onPrimary = Color(0xFF1A0F00),
    primaryContainer = EgyptGoldDark,
    onPrimaryContainer = EgyptGoldLight,
    secondary = EmeraldLight,
    onSecondary = Color.White,
    secondaryContainer = EmeraldDark,
    onSecondaryContainer = EmeraldLight,
    tertiary = InfoBlue,
    onTertiary = Color.White,
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    error = ErrorRed,
    onError = Color.White,
    errorContainer = Color(0xFF4D1515),
    onErrorContainer = ErrorRedLight,
    outline = CardBorderDark,
    outlineVariant = Color(0xFF2A3558)
)

private val LightColorScheme = lightColorScheme(
    primary = NavyDeep,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDDE5FF),
    onPrimaryContainer = NavyDeep,
    secondary = EgyptGold,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFF3DC),
    onSecondaryContainer = EgyptGoldDark,
    tertiary = EmeraldGreen,
    onTertiary = Color.White,
    background = BackgroundLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,
    error = ErrorRed,
    onError = Color.White,
    errorContainer = ErrorRedLight,
    onErrorContainer = Color(0xFF7A0000),
    outline = CardBorderLight,
    outlineVariant = Color(0xFFCCD5EE)
)

data class AppColors(
    val gold: Color,
    val goldLight: Color,
    val navy: Color,
    val emerald: Color,
    val cardBackground: Color,
    val cardBorder: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val gradientStart: Color,
    val gradientEnd: Color,
    val statusPending: Color,
    val statusPendingBg: Color,
    val statusApproved: Color,
    val statusApprovedBg: Color,
    val statusRejected: Color,
    val statusRejectedBg: Color,
    val statusCompleted: Color,
    val statusCompletedBg: Color,
    val success: Color,
    val warning: Color,
    val isDark: Boolean
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        gold = EgyptGold, goldLight = EgyptGoldLight, navy = NavyDeep,
        emerald = EmeraldGreen, cardBackground = CardLight, cardBorder = CardBorderLight,
        textPrimary = TextPrimaryLight, textSecondary = TextSecondaryLight,
        textTertiary = TextTertiaryLight, gradientStart = NavyDeep, gradientEnd = NavyMid,
        statusPending = StatusPending, statusPendingBg = StatusPendingBg,
        statusApproved = StatusApproved, statusApprovedBg = StatusApprovedBg,
        statusRejected = StatusRejected, statusRejectedBg = StatusRejectedBg,
        statusCompleted = StatusCompleted, statusCompletedBg = StatusCompletedBg,
        success = SuccessGreen, warning = WarningAmber, isDark = false
    )
}

@Composable
fun MaslahtyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}