package keemgames.footballcompanion.presentation.components.ads

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.util.concurrent.TimeUnit

class AdMobInterstitialHelper {

    private var interstitialAd: InterstitialAd? = null
    private var lastAdShowTime: Long = 0
    private val adFrequencyThrottleMillis = TimeUnit.MINUTES.toMillis(5)

    fun loadAd(activity: Activity, adUnitId: String) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(activity, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) {
                interstitialAd = ad
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                interstitialAd = null
            }
        })
    }

    fun showAdIfReady(activity: Activity, onAdClosed: () -> Unit) {
        val currentTime = System.currentTimeMillis()
        interstitialAd?.let { ad ->
            if (currentTime - lastAdShowTime >= adFrequencyThrottleMillis) {
                ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        lastAdShowTime = System.currentTimeMillis()
                        onAdClosed()
                        // Reload for next display
                        loadAd(activity, ad.adUnitId)
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        onAdClosed()
                    }

                    override fun onAdShowedFullScreenContent() {
                        lastAdShowTime = System.currentTimeMillis()
                    }
                }
                ad.show(activity)
            } else {
                onAdClosed()
            }
        } ?: run {
            onAdClosed()
        }
    }
}
