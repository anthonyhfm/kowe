package dev.anthonyhfm.compose.web.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.anthonyhfm.compose.web.data.URLPolicy

interface WebViewState {
    /**
     * This Flow contains the current url state of your webview
     */
    val location: MutableState<String?>
        get() = mutableStateOf(null)

    var policy: URLPolicy?

    val title: String?

    /**
     * Injects a javascript script in your webview.
     */
    fun evaluateJavaScript(js: String)
    fun navigateUrl(url: String)
    fun navigateHtml(html: String)
    fun reload()
}

@Composable
expect fun rememberWebViewState(url: String) : WebViewState

@Composable
expect fun rememberWebViewState(html: String? = null) : WebViewState