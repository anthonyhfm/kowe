package dev.anthonyhfm.compose.web.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import dev.anthonyhfm.compose.web.core.WebViewCore
import dev.datlag.kcef.KCEF

@Composable
actual fun WebView(
    state: WebViewState,
    modifier: Modifier
) {
    val initialized by remember { WebViewCore.initialized }

    if (initialized) {
        SwingPanel(
            factory = {
                (state as DesktopWebViewState).browser.uiComponent
            },
            modifier = modifier
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            KCEF.disposeBlocking()
        }
    }
}

