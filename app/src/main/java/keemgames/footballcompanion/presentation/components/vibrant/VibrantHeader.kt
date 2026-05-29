package keemgames.footballcompanion.presentation.components.vibrant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import keemgames.footballcompanion.presentation.theme.HeaderGradient
import keemgames.footballcompanion.presentation.theme.HighContrastWhite
import keemgames.footballcompanion.presentation.theme.TextGray

@Composable
fun VibrantHeader(
    title: String,
    subtitle: String? = null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderGradient)
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Column {
            Text(
                text = title,
                color = HighContrastWhite,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    color = TextGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
