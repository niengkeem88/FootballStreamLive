package keemgames.footballcompanion.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import keemgames.footballcompanion.presentation.components.ApiFootballWidgetView
import keemgames.footballcompanion.presentation.components.ads.MaxAdaptiveBannerAd
import keemgames.footballcompanion.presentation.components.vibrant.*

@Composable
fun HomeScreen(
    onNavigateToDetails: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        VibrantHeader(
            title = "FootballPulse",
            subtitle = "The Digital Arena"
        )
        
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            ApiFootballWidgetView(
                modifier = Modifier.fillMaxSize()
            )
        }

        MaxAdaptiveBannerAd(adUnitId = "YOUR_MAX_BANNER_AD_UNIT_ID")
    }
}
