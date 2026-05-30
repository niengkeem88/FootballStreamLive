package keemgames.footballcompanion.presentation.components.timer

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import keemgames.footballcompanion.domain.model.Match
import kotlinx.coroutines.delay

@Composable
fun MatchTimer(
    match: Match,
    modifier: Modifier = Modifier
) {
    // Tick every second to update the display
    var tick by remember { mutableStateOf(0L) }
    LaunchedEffect(match.id) {
        while (true) {
            tick = System.currentTimeMillis()
            delay(1000)
        }
    }

    val display = remember(match, tick) { computeMatchTimeDisplay(match) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Primary time text
        Text(
            text = display.primaryText,
            fontFamily = FontFamily.Monospace,
            fontSize = if (display.isLive) 28.sp else 18.sp,
            fontWeight = if (display.isLive) FontWeight.ExtraBold else FontWeight.Bold,
            color = if (display.isLive) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary,
            letterSpacing = 1.sp
        )

        // Secondary label
        if (display.secondaryText.isNotBlank()) {
            Text(
                text = display.secondaryText,
                style = MaterialTheme.typography.labelSmall,
                color = if (display.isLive) Color(0xFF4CAF50).copy(alpha = 0.8f)
                        else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Progress bar for live matches
        if (display.isLive) {
            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { display.progressFraction },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(4.dp),
                color = Color(0xFF4CAF50),
                trackColor = Color(0xFF4CAF50).copy(alpha = 0.2f),
            )
        }

        // Pulsing live indicator
        if (display.isLive) {
            Spacer(modifier = Modifier.height(4.dp))
            var visible by remember { mutableStateOf(true) }
            LaunchedEffect(match.id) {
                while (true) {
                    visible = !visible
                    delay(1000)
                }
            }
            Text(
                text = "● LIVE",
                color = if (visible) Color(0xFF4CAF50) else Color(0xFF4CAF50).copy(alpha = 0.3f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
    }
}
