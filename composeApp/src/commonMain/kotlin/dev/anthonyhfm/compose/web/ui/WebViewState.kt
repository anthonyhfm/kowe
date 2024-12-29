package dev.anthonyhfm.compose.web.ui

import androidx.compose.runtime.Composable

interface WebViewState {
    val url: String

    fun reload()

    fun loadUrl(url: String)
    fun loadHtml(html: String)
}

@Composable
expect fun rememberWebViewState(url: String) : WebViewState