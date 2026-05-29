package keemgames.footballcompanion.presentation.components.vibrant

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import keemgames.footballcompanion.presentation.theme.GlassStroke
import keemgames.footballcompanion.presentation.theme.GlassWhite

@Composable
fun GlassmorphicCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GlassWhite,
                        Color.Transparent
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        GlassStroke,
                        Color.Transparent
                    )
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}
