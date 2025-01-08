package dev.anthonyhfm.kowe.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import dev.anthonyhfm.kowe.data.JavaScriptResult
import dev.anthonyhfm.kowe.data.WebConfig
import dev.anthonyhfm.kowe.data.WebLoadingState
import dev.anthonyhfm.kowe.data.WebPolicy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface WebViewState {
    /**
     * This Flow contains the current url state of your webview
     */
    var location: String?

    var config: WebConfig
        get() = WebConfig()
        set(value) { }

    var policy: WebPolicy?
    val title: String?

    val loading: StateFlow<WebLoadingState>
        get() = MutableStateFlow(WebLoadingState.Unknown)

    /**
     * Injects a javascript script in your webview.
     */
    fun evaluateJavaScript(js: String) : JavaScriptResult

    fun loadUrl(url: String)
    fun loadHtml(html: String)
    fun reload()
}

@Composable
expect fun rememberWebViewState(
    html: String? = null,
    config: WebConfig = WebConfig(),
    policy: WebPolicy? = null,
) : WebViewState

@Composable
expect fun rememberWebViewState(
    url: String,
    config: WebConfig = WebConfig(),
    policy: WebPolicy? = null,
) : WebViewState