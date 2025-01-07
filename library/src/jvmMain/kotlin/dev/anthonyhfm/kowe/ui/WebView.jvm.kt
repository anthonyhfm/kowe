package dev.anthonyhfm.kowe.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel

@Composable
actual fun WebView(
    state: WebViewState,
    modifier: Modifier
) {
    SwingPanel(
        factory = {
            (state as ChromiumWebViewState).browser.uiComponent
        },
        modifier = modifier
    )
}