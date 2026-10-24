package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFA8C7FA),
    onPrimary = Color(0xFF003062),
    primaryContainer = Color(0xFF174779),
    onPrimaryContainer = Color(0xFFD6E3FF),

    secondary = Color(0xFF80D5BE),
    onSecondary = Color(0xFF00382C),

    background = Color(0xFF101418),
    onBackground = Color(0xFFE1E2E8),

    surface = Color(0xFF101418),
    onSurface = Color(0xFFE1E2E8),
    surfaceVariant = Color(0xFF42474E),
    onSurfaceVariant = Color(0xFFC2C7CF)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF245E91),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6E3FF),
    onPrimaryContainer = Color(0xFF001B3D),

    secondary = Color(0xFF006B56),
    onSecondary = Color.White,

    background = Color(0xFFF7F9FF),
    onBackground = Color(0xFF191C20),

    surface = Color(0xFFF7F9FF),
    onSurface = Color(0xFF191C20),
    surfaceVariant = Color(0xFFDFE3EB),
    onSurfaceVariant = Color(0xFF42474E)
)

@Composable
fun TioTecPreventivoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}