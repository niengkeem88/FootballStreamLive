package keemgames.footballcompanion.presentation.match_details

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import keemgames.footballcompanion.presentation.components.VideoEmbedPlayer
import keemgames.footballcompanion.presentation.components.vibrant.VibrantHeader
import keemgames.footballcompanion.presentation.components.vibrant.GlassmorphicCard

@Composable
fun MatchDetailsScreen(
    onNavigateBack: () -> Unit,
    viewModel: MatchDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        VibrantHeader(
            title = state.match?.title ?: "Match Center",
            subtitle = state.match?.competition
        )
        
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (state.error != null) {
            Text(text = state.error!!, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
        } else {
            state.match?.let { match ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        GlassmorphicCard {
                            if (match.highlights.isNotEmpty()) {
                                VideoEmbedPlayer(
                                    embedHtml = match.highlights.first().embedHtml,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(16 / 9f)
                                )
                            } else {
                                Text(
                                    text = "Live coverage starting soon.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                    
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Match Info",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
                            Column {
                                InfoRow(label = "Kickoff", value = match.date)
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outlineVariant)
                                InfoRow(label = "Stadium", value = "Live Arena")
                                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outlineVariant)
                                InfoRow(label = "Competition", value = match.competition)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
    }
}
