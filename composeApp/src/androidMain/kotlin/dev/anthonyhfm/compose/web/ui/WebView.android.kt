package dev.anthonyhfm.compose.web.ui

import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun WebView(
    state: WebViewState,
    modifier: Modifier
) {
    AndroidView(
        factory = {
            (state as AndroidWebViewState).webkit.apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )

                settings.javaScriptEnabled = true

                webViewClient = CustomWebViewClient()
                webChromeClient = CustomChromeClient()
            }
        },
        modifier = modifier
    )
}

class CustomChromeClient: WebChromeClient() {
    // TODO: Implementations
}

class CustomWebViewClient: WebViewClient() {
    // TODO: Implementations
}