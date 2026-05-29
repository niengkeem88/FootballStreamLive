package keemgames.footballcompanion.presentation.components.vibrant

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import keemgames.footballcompanion.presentation.theme.GlassStroke
import keemgames.footballcompanion.presentation.theme.GlassWhite
import keemgames.footballcompanion.presentation.theme.MidnightNavy

fun Modifier.stadiumShimmer() = composed {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "translation"
    )

    val shimmerColors = listOf(
        MidnightNavy,
        GlassWhite,
        GlassStroke,
        GlassWhite,
        MidnightNavy
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim, y = translateAnim)
    )

    this.background(brush)
}
