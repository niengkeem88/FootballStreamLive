package keemgames.footballcompanion.presentation.components

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ScoreBatWidgetView(
    url: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    // Remember the WebView instance across recompositions
    val webView = remember {
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient() // Basic client for video support
            
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                builtInZoomControls = false
                displayZoomControls = false
                
                // Allow media playback without user gesture for autoplay support
                mediaPlaybackRequiresUserGesture = false
                
                // Performance and security settings
                cacheMode = WebSettings.LOAD_DEFAULT
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
    }

    // Handle lifecycle: Pause when leaving composition, destroy when removed
    DisposableEffect(url) {
        webView.loadUrl(url)
        
        onDispose {
            webView.stopLoading()
            webView.onPause()
            webView.destroy()
        }
    }

    // Use AndroidView to display the WebView
    AndroidView(
        factory = { webView },
        modifier = modifier
    )
}
