package keemgames.footballcompanion.presentation.components

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoEmbedPlayer(
    embedHtml: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                
                settings.apply {
                    javaScriptEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    domStorageEnabled = true
                    mediaPlaybackRequiresUserGesture = false
                    mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                }
                
                // ScoreBat iframes usually need a specific style to fit the container
                val styledHtml = """
                    <html>
                        <body style="margin:0;padding:0;background-color:black;">
                            <div style="width:100%;height:100%;">
                                $embedHtml
                            </div>
                        </body>
                    </html>
                """.trimIndent()
                
                loadDataWithBaseURL("https://www.scorebat.com", styledHtml, "text/html", "utf-8", null)
            }
        },
        modifier = modifier,
        update = { webView ->
            // Re-load if embedHtml changes significantly, though factory usually handles first init
        },
        onRelease = { webView ->
            webView.apply {
                stopLoading()
                loadUrl("about:blank")
                clearHistory()
                removeAllViews()
                destroy()
            }
        }
    )
}
