package dev.anthonyhfm.compose.web

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import dev.anthonyhfm.compose.web.ui.rememberWebViewState
import dev.anthonyhfm.compose.web.ui.WebView
import kotlinx.coroutines.delay

@Composable
fun App() {
    val state = rememberWebViewState("https://www.jetbrains.com/compose-multiplatform/")

    WebView(
        state = state,
        modifier = Modifier
            .fillMaxSize()
    )
}