package dev.anthonyhfm.compose.web.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import dev.datlag.kcef.KCEF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.cef.browser.CefRendering

@Composable
actual fun WebView(
    state: WebViewState,
    modifier: Modifier
) {
    var initialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            KCEF.init(
                builder = {
                    progress {
                        onInitialized {
                            initialized = true
                        }
                    }
                },
                onError = {
                    it?.printStackTrace()
                }
            )
        }
    }

    if (initialized) {
        val client = remember { KCEF.newClientBlocking() }

        val browser = remember {
            client.createBrowser(
                state.url,
                CefRendering.DEFAULT,
                false
            )
        }

        SwingPanel(
            factory = {
                browser.uiComponent
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

