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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdMobBannerAd(
    adUnitId: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val activity = ctx.findActivity() ?: return@AndroidView AdView(ctx)
            AdView(activity).apply {
                setAdUnitId(adUnitId)
                setAdSize(AdSize.BANNER)
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                loadAd(AdRequest.Builder().build())
            }
        },
        onRelease = { view ->
            view.destroy()
        }
    )
}

private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
