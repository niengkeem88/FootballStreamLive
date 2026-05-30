package keemgames.footballcompanion.presentation.components.ads

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class AdMobRewardedAdHelper {

    private var rewardedAd: RewardedAd? = null

    fun loadAd(activity: Activity, adUnitId: String) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(activity, adUnitId, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdLoaded(ad: RewardedAd) {
                rewardedAd = ad
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                rewardedAd = null
            }
        })
    }

    fun showAdIfReady(
        activity: Activity,
        onRewarded: (rewardAmount: Int, rewardType: String) -> Unit,
        onAdClosed: () -> Unit
    ) {
        rewardedAd?.let { ad ->
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    onAdClosed()
                    // Reload for next use
                    loadAd(activity, ad.adUnitId)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    onAdClosed()
                }
            }
            ad.show(activity) { rewardItem: RewardItem ->
                onRewarded(rewardItem.amount, rewardItem.type)
            }
        } ?: run {
            onAdClosed()
        }
    }
}
