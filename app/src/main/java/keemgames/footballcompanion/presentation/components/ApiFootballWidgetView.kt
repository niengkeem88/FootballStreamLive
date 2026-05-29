package keemgames.footballcompanion.presentation.components

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ApiFootballWidgetView(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    val webView = remember {
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                builtInZoomControls = false
                displayZoomControls = false
                mediaPlaybackRequiresUserGesture = false
                cacheMode = WebSettings.LOAD_DEFAULT
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
        }
    }
    
    AndroidView(
        factory = { webView },
        modifier = modifier
    ) { view ->
        val htmlContent = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Football Pulse Live - Sports Dashboard</title>
                <script type="module" src="https://widgets.api-sports.io/3.1.0/widgets.js"><\/script>
                <style>
                    :root {
                        --color-bg-primary: #1e1e1e;
                        --color-bg-secondary: #2a2a2a;
                        --color-text-primary: #ffffff;
                        --color-text-secondary: #b0b0b0;
                        --color-border: #3a3a3a;
                        --spacing-md: 1.5rem;
                    }
                    * { margin: 0; padding: 0; box-sizing: border-box; }
                    html, body { width: 100%; height: 100%; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', sans-serif; }
                    body { background-color: var(--color-bg-primary); color: var(--color-text-primary); overflow: hidden; }
                    .dashboard-container { display: grid; grid-template-columns: 60% 40%; gap: var(--spacing-md); padding: var(--spacing-md); height: 100vh; overflow: hidden; }
                    .games-pane { display: flex; flex-direction: column; background-color: var(--color-bg-secondary); border-radius: 0.5rem; border: 1px solid var(--color-border); overflow-y: auto; }
                    .games-pane-header { padding: var(--spacing-md); border-bottom: 1px solid var(--color-border); flex-shrink: 0; }
                    .games-pane-header h1 { font-size: 1.5rem; font-weight: 600; }
                    .games-pane-header p { font-size: 0.875rem; color: var(--color-text-secondary); margin-top: 0.25rem; }
                    .games-content { flex: 1; overflow-y: auto; padding: var(--spacing-md); }
                    .games-content api-sports-widget { display: block; width: 100%; }
                    .details-pane { background-color: var(--color-bg-secondary); border-radius: 0.5rem; border: 1px solid var(--color-border); position: sticky; top: 0; height: 100vh; overflow-y: auto; }
                    #game-details { width: 100%; height: 100%; padding: var(--spacing-md); }
                    .details-empty { display: flex; align-items: center; justify-content: center; height: 100%; color: var(--color-text-secondary); font-size: 0.875rem; text-align: center; padding: var(--spacing-md); }
                    @media (max-width: 1023px) { .dashboard-container { grid-template-columns: 65% 35%; } }
                    @media (max-width: 767px) { .dashboard-container { grid-template-columns: 1fr; gap: 0; padding: 0; height: auto; overflow-y: auto; } .games-pane { height: auto; min-height: 100vh; border-radius: 0; border: none; border-bottom: 1px solid var(--color-border); } .details-pane { display: none; position: fixed; top: 0; left: 0; right: 0; bottom: 0; width: 100%; height: 100%; z-index: 1000; border-radius: 0; overflow-y: auto; } .details-pane.active { display: block; } }
                <\/style>
            <\/head>
            <body>
                <div class="dashboard-container">
                    <div class="games-pane">
                        <div class="games-pane-header">
                            <h1>Live Games<\/h1>
                            <p>Select a game to view details<\/p>
                        <\/div>
                        <div class="games-content">
                            <api-sports-widget data-type="games"><\/api-sports-widget>
                        <\/div>
                    <\/div>
                    <div class="details-pane">
                        <div id="game-details">
                            <div class="details-empty">Select a game from the list to view details<\/div>
                        <\/div>
                    <\/div>
                <\/div>
                <api-sports-widget
                    data-type="config"
                    data-key="c87787f019e70ab9bd72d7391634e2b0"
                    data-sport="football"
                    data-lang="en"
                    data-theme="grey"
                    data-show-errors="true"
                    data-target-game="#game-details"
                    style="display: none;"
                ><\/api-sports-widget>
            <\/body>
            <\/html>
        """.trimIndent()
        
        view.loadDataWithBaseURL(
            "https://api-sports.io/",
            htmlContent,
            "text/html",
            "utf-8",
            null
        )
    }
}
