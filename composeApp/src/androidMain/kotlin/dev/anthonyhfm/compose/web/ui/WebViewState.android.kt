package dev.anthonyhfm.compose.web.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.anthonyhfm.compose.web.data.WebLoadingState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class AndroidWebViewState(
    override val url: String
) : WebViewState {
    override val loading: SharedFlow<WebLoadingState> = MutableSharedFlow()

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