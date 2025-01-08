package dev.anthonyhfm.kowe.ui

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import dev.anthonyhfm.kowe.clients.KoweWebChromeClient
import dev.anthonyhfm.kowe.clients.KoweWebViewClient

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

                webViewClient = KoweWebViewClient(state)
                webChromeClient = KoweWebChromeClient(state)
            }
        },
        modifier = modifier
    )
}