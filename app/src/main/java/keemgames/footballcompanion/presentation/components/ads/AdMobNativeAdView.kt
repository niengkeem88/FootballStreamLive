package keemgames.footballcompanion.presentation.components.ads

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import keemgames.footballcompanion.R

@Composable
fun AdMobNativeAdView(
    adUnitId: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }

    // Load native ad on first composition
    DisposableEffect(adUnitId) {
        val adLoader = AdLoader.Builder(context, adUnitId)
            .forNativeAd { ad: NativeAd ->
                nativeAd = ad
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    nativeAd = null
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder().build()
            )
            .build()

        adLoader.loadAd(AdRequest.Builder().build())

        onDispose {
            nativeAd?.destroy()
        }
    }

    nativeAd?.let { ad ->
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .then(
                    Modifier.wrapContentSize() // uses the native ad view background
                )
        ) {
            AndroidView(
                factory = { ctx ->
                    val adView = LayoutInflater.from(ctx)
                        .inflate(R.layout.admob_native_ad_layout, null) as NativeAdView

                    populateNativeAdView(ad, adView)
                    adView
                },
                modifier = Modifier.fillMaxWidth(),
                onRelease = { ad.destroy() }
            )
        }
    }
}

private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
    // Register the ad view with the native ad
    adView.headlineView = adView.findViewById(R.id.ad_headline)
    adView.bodyView = adView.findViewById(R.id.ad_body)
    adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
    adView.iconView = adView.findViewById(R.id.ad_app_icon)
    adView.mediaView = adView.findViewById(R.id.ad_media)
    adView.starRatingView = adView.findViewById(R.id.ad_stars)
    adView.storeView = adView.findViewById(R.id.ad_store)
    adView.priceView = adView.findViewById(R.id.ad_price)
    adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

    // Headline
    (adView.headlineView as? TextView)?.text = nativeAd.headline

    // Body
    if (nativeAd.body != null) {
        (adView.bodyView as? TextView)?.text = nativeAd.body
        adView.bodyView?.visibility = View.VISIBLE
    } else {
        adView.bodyView?.visibility = View.INVISIBLE
    }

    // Call to action
    if (nativeAd.callToAction != null) {
        (adView.callToActionView as? Button)?.text = nativeAd.callToAction
        adView.callToActionView?.visibility = View.VISIBLE
    } else {
        adView.callToActionView?.visibility = View.INVISIBLE
    }

    // Icon
    if (nativeAd.icon != null) {
        (adView.iconView as? ImageView)?.setImageDrawable(nativeAd.icon?.drawable)
        adView.iconView?.visibility = View.VISIBLE
    } else {
        adView.iconView?.visibility = View.GONE
    }

    // Star rating
    if (nativeAd.starRating != null && nativeAd.starRating!! > 0) {
        (adView.starRatingView as? RatingBar)?.rating = nativeAd.starRating!!.toFloat()
        adView.starRatingView?.visibility = View.VISIBLE
    } else {
        adView.starRatingView?.visibility = View.INVISIBLE
    }

    // Store
    if (nativeAd.store != null) {
        (adView.storeView as? TextView)?.text = nativeAd.store
        adView.storeView?.visibility = View.VISIBLE
    } else {
        adView.storeView?.visibility = View.INVISIBLE
    }

    // Price
    if (nativeAd.price != null) {
        (adView.priceView as? TextView)?.text = nativeAd.price
        adView.priceView?.visibility = View.VISIBLE
    } else {
        adView.priceView?.visibility = View.INVISIBLE
    }

    // Advertiser
    if (nativeAd.advertiser != null) {
        (adView.advertiserView as? TextView)?.text = nativeAd.advertiser
        adView.advertiserView?.visibility = View.VISIBLE
    } else {
        adView.advertiserView?.visibility = View.INVISIBLE
    }

    // Media (required for native ad content)
    if (nativeAd.mediaContent != null) {
        (adView.mediaView as? MediaView)?.setMediaContent(nativeAd.mediaContent!!)
        adView.mediaView?.visibility = View.VISIBLE
    }

    // Set the native ad
    adView.setNativeAd(nativeAd)
}
