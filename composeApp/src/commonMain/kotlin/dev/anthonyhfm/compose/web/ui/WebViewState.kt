package dev.anthonyhfm.compose.web.ui

import androidx.compose.runtime.Composable
import dev.anthonyhfm.compose.web.data.WebLoadingState
import kotlinx.coroutines.flow.SharedFlow

interface WebViewState {
    val url: String

    val loading: SharedFlow<WebLoadingState>

    fun reload()

    fun loadUrl(url: String)
    fun loadHtml(html: String)
}

@Composable
expect fun rememberWebViewState(url: String) : WebViewState