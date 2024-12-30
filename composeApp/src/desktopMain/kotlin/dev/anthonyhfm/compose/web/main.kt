package dev.anthonyhfm.compose.web

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.anthonyhfm.compose.web.core.WebViewCore

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose WebView",
    ) {
        LaunchedEffect(Unit) {
            WebViewCore.init()
        }

        if (WebViewCore.initialized.value) {
            App()
        }
    }
}