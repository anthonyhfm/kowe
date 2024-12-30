package dev.anthonyhfm.compose.web

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.anthonyhfm.compose.web.core.WebViewCore

fun main() = application {
    WebViewCore.init()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose WebView",
    ) {
        App()
    }
}