package com.safeflow.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Shapes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

private val SoftUIColorScheme = lightColorScheme(
    primary = SoftBlue,
    onPrimary = White,
    secondary = SoftMintGreen,
    onSecondary = DarkGray,
    background = White,
    onBackground = DarkGray,
    surface = White,
    onSurface = DarkGray,
    error = SoftRed,
    onError = White
)

private val SoftUIShapes = Shapes(
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(24.dp),
    large = RoundedCornerShape(50.dp) // Pilule
)

@Composable
fun SafeFlowTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SoftUIColorScheme,
        shapes = SoftUIShapes,
        content = content
    )
}
