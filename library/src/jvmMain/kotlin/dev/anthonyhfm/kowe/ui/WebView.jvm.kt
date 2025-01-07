package dev.anthonyhfm.kowe.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import dev.anthonyhfm.kowe.desktop.WebViewCore

@Composable
actual fun WebView(
    state: WebViewState,
    modifier: Modifier
) {
    if (WebViewCore.initialized.value) {
        SwingPanel(
            factory = {
                (state as ChromiumWebViewState).browser.uiComponent
            },
            modifier = modifier
        )
    }
}