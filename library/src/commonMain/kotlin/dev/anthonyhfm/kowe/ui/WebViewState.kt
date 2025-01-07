package dev.anthonyhfm.kowe.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import dev.anthonyhfm.kowe.data.WebConfig
import dev.anthonyhfm.kowe.data.WebPolicy

interface WebViewState {
    /**
     * This Flow contains the current url state of your webview
     */
    var location: String?

    var config: WebConfig
        get() = WebConfig()
        set(value) { }

    var policy: WebPolicy?
    var title: String?

    /**
     * Injects a javascript script in your webview.
     */
    fun evaluateJavaScript(js: String)
    fun loadUrl(url: String)
    fun loadHtml(html: String)
    fun reload()
}

@Composable
expect fun rememberWebViewState(
    html: String? = null,
    policy: WebPolicy? = null,
) : WebViewState

@Composable
expect fun rememberWebViewState(
    url: String,
    policy: WebPolicy? = null,
) : WebViewState