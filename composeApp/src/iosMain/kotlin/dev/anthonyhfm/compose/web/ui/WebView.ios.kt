package dev.anthonyhfm.compose.web.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView

@Composable
actual fun WebView(
    state: WebViewState,
    modifier: Modifier
) {
    UIKitView(
        modifier = modifier,
        factory = {
            return@UIKitView (state as AppleWebViewState).wkWebView
        },
    )
}