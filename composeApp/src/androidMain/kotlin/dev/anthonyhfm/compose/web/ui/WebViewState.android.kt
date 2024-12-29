package dev.anthonyhfm.compose.web.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

class AndroidWebViewState(
    override val url: String
) : WebViewState {
    override fun loadUrl(url: String) {
        TODO("Not yet implemented")
    }

    override fun loadHtml(html: String) {
        TODO("Not yet implemented")
    }

    override fun reload() {
        TODO("Not yet implemented")
    }
}

@Composable
actual fun rememberWebViewState(url: String): WebViewState {
    val state = remember {
        AndroidWebViewState(url)
    }

    return state
}