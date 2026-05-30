package keemgames.footballcompanion.presentation.home

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import keemgames.footballcompanion.domain.model.Match
import keemgames.footballcompanion.presentation.components.ads.AdMobBannerAd
import keemgames.footballcompanion.presentation.components.ads.AdMobInterstitialHelper
import keemgames.footballcompanion.presentation.components.ads.AdMobNativeAdView
import keemgames.footballcompanion.presentation.components.vibrant.*
import keemgames.footballcompanion.presentation.components.LeagueSectionHeader
import keemgames.footballcompanion.presentation.components.MatchCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetails: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity

    val interstitialHelper = remember { AdMobInterstitialHelper() }
    LaunchedEffect(Unit) {
        activity?.let { interstitialHelper.loadAd(it, "ca-app-pub-3940256099942544/1033173712") }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        VibrantHeader(title = "FootballPulse", subtitle = "The Digital Arena")

        val tabs = listOf("Live", "Upcoming", "Completed")
        TabRow(
            selectedTabIndex = state.selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = state.selectedTab == index
                Tab(
                    selected = isSelected,
                    onClick = { viewModel.selectTab(index) },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(title, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                            val count = when (index) { 0 -> state.liveMatches.size; 1 -> state.upcomingMatches.size; 2 -> state.completedMatches.size; else -> 0 }
                            if (count > 0) {
                                Spacer(Modifier.width(6.dp))
                                Surface(
                                    shape = MaterialTheme.shapes.small,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                                ) {
                                    Text("$count", Modifier.padding(horizontal = 6.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }
                    }
                )
            }
        }

        when {
            state.isLoading -> {
                Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
            state.error != null -> {
                Box(Modifier.weight(1f).fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(state.error ?: "", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyLarge)
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { viewModel.getMatches() }) { Text("Retry") }
                    }
                }
            }
            else -> {
                val groupedMatches: Map<String, List<Match>> = when (state.selectedTab) {
                    0 -> state.groupedLive; 1 -> state.groupedUpcoming; 2 -> state.groupedCompleted; else -> emptyMap()
                }

                if (groupedMatches.isEmpty()) {
                    Box(Modifier.weight(1f).fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                        val emptyText = when (state.selectedTab) { 0 -> "No live matches right now"; 1 -> "No upcoming fixtures"; 2 -> "No completed matches"; else -> "No matches found" }
                        Text(emptyText, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyLarge)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        groupedMatches.entries.forEachIndexed { leagueIndex, (league, matches) ->
                            // League header
                            item(key = "header_$league") {
                                val badge = matches.firstOrNull()?.leagueBadge
                                LeagueSectionHeader(leagueName = league, leagueBadge = badge, matchCount = matches.size)
                            }

                            // Match cards with in-content banners every 3 matches
                            matches.forEachIndexed { matchIndex, match ->
                                item(key = match.id) {
                                    MatchCard(match = match, onClick = {
                                        activity?.let { act ->
                                            interstitialHelper.showAdIfReady(act) { onNavigateToDetails(match.id) }
                                        } ?: onNavigateToDetails(match.id)
                                    })
                                }

                                // In-content banner ad every 3 matches for a premium look
                                if ((matchIndex + 1) % 3 == 0 && matchIndex < matches.size - 1) {
                                    item(key = "banner_${league}_$matchIndex") {
                                        Spacer(Modifier.height(4.dp))
                                        AdMobBannerAd(
                                            adUnitId = "ca-app-pub-3940256099942544/6300978111",
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        Spacer(Modifier.height(4.dp))
                                    }
                                }
                            }

                            // Native ad after league sections
                            if (leagueIndex > 0 && leagueIndex % 2 == 0) {
                                item(key = "native_$league") {
                                    AdMobNativeAdView(adUnitId = "ca-app-pub-3940256099942544/2247696110", modifier = Modifier.fillMaxWidth())
                                }
                            }

                            item(key = "spacer_$league") { Spacer(Modifier.height(8.dp)) }
                        }
                    }
                }
            }
        }

        AdMobBannerAd(adUnitId = "ca-app-pub-3940256099942544/6300978111", modifier = Modifier.fillMaxWidth())
    }
}
