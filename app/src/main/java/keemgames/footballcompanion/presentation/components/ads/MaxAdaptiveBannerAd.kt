package keemgames.footballcompanion.presentation.components.ads

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.applovin.mediation.MaxAdFormat
import com.applovin.mediation.ads.MaxAdView
import com.applovin.sdk.AppLovinSdkUtils

@Composable
fun MaxAdaptiveBannerAd(
    adUnitId: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val activity = ctx.findActivity() ?: throw IllegalStateException("Context must be an Activity")
            MaxAdView(adUnitId, activity).apply {
                val width = ViewGroup.LayoutParams.MATCH_PARENT
                val heightDp = MaxAdFormat.BANNER.getAdaptiveSize(activity).height
                val heightPx = AppLovinSdkUtils.dpToPx(activity, heightDp)
                
                layoutParams = FrameLayout.LayoutParams(width, heightPx)
                setExtraParameter("adaptive_banner", "true")
                loadAd()
            }
        },
        onRelease = { view ->
            view.destroy()
        }
    )
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
