package keemgames.footballcompanion.presentation.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// --- Base Tokens: Midnight Stadium ---
val ObsidianBlack = Color(0xFF0A0E1A)
val MidnightNavy = Color(0xFF121A2D)
val DeepSapphire = Color(0xFF1A233A)
val SlateGray = Color(0xFF2C3548)

// --- Accent Tokens: High Energy ---
val NeonPitchGreen = Color(0xFF00FF66)
val LaserLime = Color(0xFFCCFF00)
val LiveCrimson = Color(0xFFFF3B30)
val GoalOrange = Color(0xFFFF9500)

// --- Surface Tokens: Premium Depth ---
val GlassWhite = Color(0x1AFFFFFF)
val GlassStroke = Color(0x33FFFFFF)
val HighContrastWhite = Color(0xFFF5F5F7)
val TextGray = Color(0xFFA1A1AA)

// --- Gradient Presets ---
val HeaderGradient = Brush.verticalGradient(
    colors = listOf(DeepSapphire, ObsidianBlack)
)

val HeroBannerGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF1E3A8A), MidnightNavy)
)

val LivePulseGradient = Brush.radialGradient(
    colors = listOf(LiveCrimson.copy(alpha = 0.6f), Color.Transparent)
)
