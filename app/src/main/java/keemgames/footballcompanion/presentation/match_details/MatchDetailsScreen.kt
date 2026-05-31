package keemgames.footballcompanion.presentation.match_details

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import keemgames.footballcompanion.domain.repository.PlayerInfo
import keemgames.footballcompanion.domain.repository.StandingEntry
import keemgames.footballcompanion.presentation.components.MatchStatusBadge
import keemgames.footballcompanion.presentation.components.timer.MatchTimer
import keemgames.footballcompanion.presentation.components.VideoEmbedPlayer
import keemgames.footballcompanion.presentation.components.ads.AdMobBannerAd
import keemgames.footballcompanion.presentation.components.ads.AdMobRewardedAdHelper
import keemgames.footballcompanion.presentation.components.vibrant.VibrantHeader
import keemgames.footballcompanion.presentation.components.vibrant.GlassmorphicCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchDetailsScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: MatchDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity

    val rewardedHelper = remember { AdMobRewardedAdHelper() }
    LaunchedEffect(Unit) {
        activity?.let { rewardedHelper.loadAd(it, "ca-app-pub-3940256099942544/5224354917") }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header with back button
        Box(modifier = Modifier.fillMaxWidth()) {
            VibrantHeader(
                title = state.match?.let { "${it.homeTeam} vs ${it.awayTeam}" } ?: "Match Center",
                subtitle = state.match?.competition
            )
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.align(Alignment.TopStart).padding(top = 32.dp, start = 8.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }

        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        } else if (state.error != null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(state.error!!, color = MaterialTheme.colorScheme.error)
            }
        } else {
            state.match?.let { match ->
                // Tab Row
                val tabs = listOf("Overview", "Lineups", "Standings", "Stats")
                TabRow(
                    selectedTabIndex = state.selectedTab,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = state.selectedTab == index,
                            onClick = { viewModel.selectTab(index) },
                            text = {
                                Text(
                                    title,
                                    fontWeight = if (state.selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 12.sp
                                )
                            }
                        )
                    }
                }

                when (state.selectedTab) {
                    0 -> OverviewTab(match, rewardedHelper, activity)
                    1 -> LineupsTab(state.homePlayers, state.awayPlayers, match, state.playersLoading)
                    2 -> StandingsTab(state.standings, state.standingsLoading)
                    3 -> StatsTab(match)
                }
            }
        }
    }
}

// ====================== OVERVIEW TAB ======================

@Composable
private fun OverviewTab(
    match: keemgames.footballcompanion.domain.model.Match,
    state: MatchDetailsState,
    rewardedHelper: AdMobRewardedAdHelper,
    activity: Activity?
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Scoreboard card
        item { ScoreboardCard(match) }

        // In-content banner ad between sections
        item {
            AdMobBannerAd(
                adUnitId = "ca-app-pub-3940256099942544/6300978111",
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Highlights
        item { HighlightsSection(match, rewardedHelper, activity) }

        // Match info
        item { MatchInfoCard(match) }
    }
}

@Composable
private fun ScoreboardCard(match: keemgames.footballcompanion.domain.model.Match) {
    GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            MatchStatusBadge(status = match.status)
            Spacer(modifier = Modifier.height(8.dp))
            MatchTimer(match = match)
            Spacer(modifier = Modifier.height(4.dp))
            Spacer(Modifier.height(16.dp))
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (!match.homeTeamBadge.isNullOrBlank()) {
                        AsyncImage(
                            model = match.homeTeamBadge,
                            contentDescription = "${match.homeTeam} badge",
                            modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(match.homeTeam, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "${match.homeScore ?: "-"} : ${match.awayScore ?: "-"}",
                        fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (!match.awayTeamBadge.isNullOrBlank()) {
                        AsyncImage(
                            model = match.awayTeamBadge,
                            contentDescription = "${match.awayTeam} badge",
                            modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(match.awayTeam, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            }
            if (match.date.isNotBlank()) {
                Spacer(Modifier.height(12.dp))
                Text(match.date, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun HighlightsSection(
    match: keemgames.footballcompanion.domain.model.Match,
    rewardedHelper: AdMobRewardedAdHelper,
    activity: Activity?
) {
    if (match.highlights.isNotEmpty()) {
        GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp)) {
                Text("Match Highlights", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                VideoEmbedPlayer(
                    embedHtml = match.highlights.first().embedHtml,
                    modifier = Modifier.fillMaxWidth().aspectRatio(16 / 9f)
                )
            }
        }
    } else {
        GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.PlayArrow, null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.height(12.dp))
                Text("Highlights Not Available", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text("Watch a short ad to unlock", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
                Spacer(Modifier.height(16.dp))
                Button(onClick = {
                    activity?.let { act ->
                        rewardedHelper.showAdIfReady(act, { amount, type ->
                            Toast.makeText(act, "Earned $amount $type", Toast.LENGTH_SHORT).show()
                        }) {
                            Toast.makeText(act, "Highlights coming soon", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) { Text("Watch Ad to Unlock") }
            }
        }
    }
}

@Composable
private fun MatchInfoCard(match: keemgames.footballcompanion.domain.model.Match) {
    GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text("Match Info", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            InfoRow("Competition", match.competition)
            if (match.date.isNotBlank()) { HorizontalDivider(Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outlineVariant); InfoRow("Date", match.date) }
            if (match.status.isNotBlank()) { HorizontalDivider(Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outlineVariant); InfoRow("Status", match.status) }
            if (match.venue.isNotBlank()) { HorizontalDivider(Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outlineVariant); InfoRow("Venue", match.venue) }
        }
    }
}

// ====================== LINEUPS TAB ======================

@Composable
private fun LineupsTab(
    homePlayers: List<PlayerInfo>,
    awayPlayers: List<PlayerInfo>,
    match: keemgames.footballcompanion.domain.model.Match,
    isLoading: Boolean
) {
    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else if (homePlayers.isEmpty() && awayPlayers.isEmpty()) {
        Box(Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
            Text("Lineups not available", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Home team squad
            item { TeamSquadHeader(match.homeTeam, match.homeTeamBadge) }
            items(homePlayers) { player -> PlayerRow(player) }

            // Banner ad between team sections
            item {
                Spacer(Modifier.height(8.dp))
                AdMobBannerAd(adUnitId = "ca-app-pub-3940256099942544/6300978111", modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(8.dp))
            }

            // Away team squad
            item { TeamSquadHeader(match.awayTeam, match.awayTeamBadge) }
            items(awayPlayers) { player -> PlayerRow(player) }
        }
    }
}

@Composable
private fun TeamSquadHeader(teamName: String, badgeUrl: String?) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        if (!badgeUrl.isNullOrBlank()) {
            AsyncImage(
                model = badgeUrl,
                contentDescription = teamName,
                modifier = Modifier.size(28.dp).clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.width(10.dp))
        }
        Text("$teamName Squad", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun PlayerRow(player: PlayerInfo) {
    GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Jersey number
            if (!player.number.isNullOrBlank()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                ) {
                    Text(
                        player.number,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(Modifier.width(12.dp))
            }

            // Player photo
            if (!player.thumb.isNullOrBlank()) {
                AsyncImage(
                    model = player.thumb,
                    contentDescription = player.name,
                    modifier = Modifier.size(36.dp).clip(RoundedCornerShape(18.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(12.dp))
            }

            Column(Modifier.weight(1f)) {
                Text(player.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                if (!player.position.isNullOrBlank()) {
                    Text(player.position, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            if (!player.nationality.isNullOrBlank()) {
                Text(player.nationality, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

// ====================== STANDINGS TAB ======================

@Composable
private fun StandingsTab(standings: List<StandingEntry>, isLoading: Boolean) {
    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    } else if (standings.isEmpty()) {
        Box(Modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
            Text("Standings not available", color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Table header
            item {
                GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp)) {
                        Text("#", Modifier.width(24.dp), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("Team", Modifier.weight(1f), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("P", Modifier.width(24.dp), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
                        Text("W", Modifier.width(24.dp), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
                        Text("D", Modifier.width(24.dp), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
                        Text("L", Modifier.width(24.dp), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
                        Text("GD", Modifier.width(28.dp), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
                        Text("Pts", Modifier.width(30.dp), style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
                    }
                }
            }

            // Table rows
            items(standings) { entry ->
                StandingRow(entry)
            }
        }
    }
}

@Composable
private fun StandingRow(entry: StandingEntry) {
    GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rank
            val rankColor = when (entry.rank) {
                1 -> Color(0xFFFFD700)
                2 -> Color(0xFFC0C0C0)
                3 -> Color(0xFFCD7F32)
                else -> MaterialTheme.colorScheme.onSurface
            }
            Text("${entry.rank}", Modifier.width(24.dp), fontWeight = FontWeight.Bold, color = rankColor)

            // Team badge + name
            Row(Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                if (!entry.teamBadge.isNullOrBlank()) {
                    AsyncImage(
                        model = entry.teamBadge,
                        contentDescription = entry.teamName,
                        modifier = Modifier.size(22.dp).clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(Modifier.width(8.dp))
                }
                Text(entry.teamName, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
            }

            Text("${entry.played}", Modifier.width(24.dp), style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
            Text("${entry.wins}", Modifier.width(24.dp), style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
            Text("${entry.draws}", Modifier.width(24.dp), style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
            Text("${entry.losses}", Modifier.width(24.dp), style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)

            val gdColor = when {
                entry.goalDiff > 0 -> Color(0xFF4CAF50)
                entry.goalDiff < 0 -> Color(0xFFF44336)
                else -> MaterialTheme.colorScheme.onSurface
            }
            val gdText = if (entry.goalDiff > 0) "+${entry.goalDiff}" else "${entry.goalDiff}"
            Text(gdText, Modifier.width(28.dp), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, color = gdColor, textAlign = TextAlign.Center)

            Text("${entry.points}", Modifier.width(30.dp), style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center)
        }
    }
}

// ====================== STATS TAB ======================

@Composable
private fun StatsTab(match: keemgames.footballcompanion.domain.model.Match) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Match Statistics", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(16.dp))

                    StatRow("Score", "${match.homeScore ?: "-"} : ${match.awayScore ?: "-"}")
                    StatRow("Competition", match.competition)
                    StatRow("Status", match.status)
                    StatRow("Date", match.date)
                    if (match.venue.isNotBlank()) StatRow("Venue", match.venue)
                    StatRow("Home Team", match.homeTeam)
                    StatRow("Away Team", match.awayTeam)
                }
            }
        }

        // Banner ad at the bottom of stats
        item {
            AdMobBannerAd(
                adUnitId = "ca-app-pub-3940256099942544/6300978111",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
        Text(value, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
    }
}
