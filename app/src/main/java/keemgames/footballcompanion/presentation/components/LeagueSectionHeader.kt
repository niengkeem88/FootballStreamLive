package keemgames.footballcompanion.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun LeagueSectionHeader(
    leagueName: String,
    leagueBadge: String?,
    matchCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!leagueBadge.isNullOrBlank()) {
            AsyncImage(
                model = leagueBadge,
                contentDescription = leagueName,
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(10.dp))
        }

        Text(
            text = leagueName,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f)
        )

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
        ) {
            Text(
                text = "$matchCount",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
