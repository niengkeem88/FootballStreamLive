package keemgames.footballcompanion.presentation.home

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import keemgames.footballcompanion.presentation.components.ads.AdMobBannerAd
import keemgames.footballcompanion.presentation.components.ads.AdMobInterstitialHelper
import keemgames.footballcompanion.presentation.components.ads.AdMobNativeAdView
import keemgames.footballcompanion.presentation.components.vibrant.*
import keemgames.footballcompanion.presentation.components.MatchCard

@Composable
fun HomeScreen(
    onNavigateToDetails: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val activity = context as? Activity

    // Load interstitial ad on first composition
    val interstitialHelper = remember { AdMobInterstitialHelper() }
    LaunchedEffect(Unit) {
        activity?.let {
            interstitialHelper.loadAd(
                it,
                adUnitId = "ca-app-pub-3940256099942544/1033173712" // Test interstitial
            )
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        VibrantHeader(
            title = "FootballPulse",
            subtitle = "The Digital Arena"
        )

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                        Text(
                            text = state.error ?: "",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.getMatches() }) {
                            Text("Retry")
                        }
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    // Insert a native ad after every 5 matches
                    itemsIndexed(state.matches, key = { _, match -> match.id }) { index, match ->
                        MatchCard(
                            match = match,
                            onClick = {
                                activity?.let { act ->
                                    interstitialHelper.showAdIfReady(act) {
                                        onNavigateToDetails(match.id)
                                    }
                                } ?: onNavigateToDetails(match.id)
                            }
                        )

                        // Insert native ad every 5 items
                        if ((index + 1) % 5 == 0) {
                            AdMobNativeAdView(
                                adUnitId = "ca-app-pub-3940256099942544/2247696110", // Test native
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    if (state.matches.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                contentAlignment = androidx.compose.ui.Alignment.Center
                            ) {
                                Text(
                                    text = "No matches found",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }
            }
        }

        AdMobBannerAd(
            adUnitId = "ca-app-pub-3940256099942544/6300978111",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
