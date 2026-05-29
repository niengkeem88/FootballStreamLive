package keemgames.footballcompanion.presentation.components.ads

import android.app.Activity
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaxInterstitialHelper @Inject constructor() {

    private var interstitialAd: MaxInterstitialAd? = null
    private var lastAdShowTime: Long = 0
    private val adFrequencyThrottleMillis = TimeUnit.MINUTES.toMillis(5)

    fun loadAd(activity: Activity, adUnitId: String) {
        if (interstitialAd == null) {
            interstitialAd = MaxInterstitialAd(adUnitId, activity)
            interstitialAd?.setListener(object : MaxAdListener {
                override fun onAdLoaded(ad: MaxAd) {}
                override fun onAdDisplayed(ad: MaxAd) {
                    lastAdShowTime = System.currentTimeMillis()
                }
                override fun onAdHidden(ad: MaxAd) {
                    interstitialAd?.loadAd()
                }
                override fun onAdClicked(ad: MaxAd) {}
                override fun onAdLoadFailed(adUnitId: String, error: MaxError) {}
                override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                    interstitialAd?.loadAd()
                }
            })
        }
        interstitialAd?.loadAd()
    }

    fun showAdIfReady(activity: Activity, onAdClosed: () -> Unit) {
        val currentTime = System.currentTimeMillis()
        if (interstitialAd?.isReady == true && (currentTime - lastAdShowTime >= adFrequencyThrottleMillis)) {
            interstitialAd?.setListener(object : MaxAdListener {
                override fun onAdLoaded(ad: MaxAd) {}
                override fun onAdDisplayed(ad: MaxAd) {
                    lastAdShowTime = System.currentTimeMillis()
                }
                override fun onAdHidden(ad: MaxAd) {
                    onAdClosed()
                    interstitialAd?.loadAd()
                }
                override fun onAdClicked(ad: MaxAd) {}
                override fun onAdLoadFailed(adUnitId: String, error: MaxError) {}
                override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                    onAdClosed()
                    interstitialAd?.loadAd()
                }
            })
            interstitialAd?.showAd()
        } else {
            onAdClosed()
        }
    }
}
