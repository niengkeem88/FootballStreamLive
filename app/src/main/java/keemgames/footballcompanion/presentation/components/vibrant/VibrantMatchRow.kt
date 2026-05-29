package keemgames.footballcompanion.presentation.components.vibrant

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import keemgames.footballcompanion.presentation.theme.HighContrastWhite
import keemgames.footballcompanion.presentation.theme.TextGray

@Composable
fun VibrantMatchRow(
    homeTeam: String,
    awayTeam: String,
    homeLogo: String,
    awayLogo: String,
    competition: String,
    time: String,
    isLive: Boolean = false,
    onClick: () -> Unit
) {
    GlassmorphicCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .bounceClick(onClick = onClick)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = competition.uppercase(),
                    color = TextGray,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                
                if (isLive) {
                    LivePulseIndicator()
                } else {
                    Text(
                        text = time,
                        color = HighContrastWhite,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Home Team
                TeamSection(name = homeTeam, logoUrl = homeLogo, modifier = Modifier.weight(1f))
                
                // VS / Score
                Text(
                    text = "VS",
                    color = HighContrastWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Away Team
                TeamSection(name = awayTeam, logoUrl = awayLogo, modifier = Modifier.weight(1f), isReversed = true)
            }
        }
    }
}

@Composable
private fun TeamSection(
    name: String,
    logoUrl: String,
    modifier: Modifier = Modifier,
    isReversed: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (isReversed) Arrangement.End else Arrangement.Start
    ) {
        if (!isReversed) {
            TeamLogo(logoUrl)
            Spacer(modifier = Modifier.width(12.dp))
            TeamName(name)
        } else {
            TeamName(name)
            Spacer(modifier = Modifier.width(12.dp))
            TeamLogo(logoUrl)
        }
    }
}

@Composable
private fun TeamLogo(url: String) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun TeamName(name: String) {
    Text(
        text = name,
        color = HighContrastWhite,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 1
    )
}
