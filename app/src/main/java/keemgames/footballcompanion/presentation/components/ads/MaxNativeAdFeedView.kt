package keemgames.footballcompanion.presentation.components.ads

import android.view.LayoutInflater
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import keemgames.footballcompanion.R
import keemgames.footballcompanion.presentation.theme.MidnightNavy

@Composable
fun MaxNativeAdFeedView(
    adUnitId: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<MaxAd?>(null) }
    
    val adLoader = remember {
        MaxNativeAdLoader(adUnitId, context).apply {
            setNativeAdListener(object : MaxNativeAdListener() {
                override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd) {
                    nativeAd = ad
                }

                override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError) {
                    // Handle failure
                }

                override fun onNativeAdClicked(ad: MaxAd) {}
            })
        }
    }

    DisposableEffect(Unit) {
        adLoader.loadAd()
        onDispose {
            adLoader.destroy()
        }
    }

    if (nativeAd != null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MidnightNavy)
        ) {
            AndroidView(
                factory = { ctx ->
                    val adView = LayoutInflater.from(ctx).inflate(R.layout.max_native_ad_layout, null) as MaxNativeAdView
                    adLoader.render(adView, nativeAd)
                    adView
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
