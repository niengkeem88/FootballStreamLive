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
                
                // Wrap the embed HTML to fit container properly
                val styledHtml = """
                    <html>
                        <head>
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <style>
                                * { margin: 0; padding: 0; box-sizing: border-box; }
                                html, body { width: 100%; height: 100%; background-color: #000; overflow: hidden; }
                                .embed-container { width: 100%; height: 100%; display: flex; align-items: center; justify-content: center; }
                                iframe { max-width: 100%; max-height: 100%; border: none; }
                            </style>
                        </head>
                        <body>
                            <div class="embed-container">
                                $embedHtml
                            </div>
                        </body>
                    </html>
                """.trimIndent()
                
                loadDataWithBaseURL(
                    "https://www.thesportsdb.com",
                    styledHtml,
                    "text/html",
                    "utf-8",
                    null
                )
            }
        },
        modifier = modifier,
        update = { },
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
