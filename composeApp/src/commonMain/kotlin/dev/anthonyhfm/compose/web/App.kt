package dev.anthonyhfm.compose.web

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import dev.anthonyhfm.compose.web.ui.rememberWebViewState
import dev.anthonyhfm.compose.web.ui.WebView

@Composable
fun App() {
    val state = rememberWebViewState(url = "https://example.com")

    WebView(
        state = state,
        modifier = Modifier
            .fillMaxSize()
    )
}