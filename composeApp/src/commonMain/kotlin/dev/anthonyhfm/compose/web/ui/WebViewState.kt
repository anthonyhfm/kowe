package dev.anthonyhfm.compose.web.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import dev.anthonyhfm.compose.web.data.URLPolicy
import dev.anthonyhfm.compose.web.data.WebConfig

interface WebViewState {
    /**
     * This Flow contains the current url state of your webview
     */
    val location: MutableState<String?>
        get() = mutableStateOf(null)

    var config: WebConfig
        get() = WebConfig()
        set(value) { }

    var policy: URLPolicy?
    val title: MutableState<String?>

    /**
     * Injects a javascript script in your webview.
     */
    fun evaluateJavaScript(js: String)
    fun navigateUrl(url: String)
    fun navigateHtml(html: String)
    fun reload()
}

@Composable
expect fun rememberWebViewState(
    html: String? = null,
    urlPolicy: URLPolicy? = null,
) : WebViewState

@Composable
expect fun rememberWebViewState(
    url: String,
    urlPolicy: URLPolicy? = null,
) : WebViewState