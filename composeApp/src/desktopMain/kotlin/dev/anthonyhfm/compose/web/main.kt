package dev.anthonyhfm.compose.web

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose WebView",
    ) {
        App()
    }
}