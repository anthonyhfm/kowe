package dev.anthonyhfm.kowe.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import dev.anthonyhfm.kowe.data.ConsoleMessage
import dev.anthonyhfm.kowe.data.JavaScriptResult
import dev.anthonyhfm.kowe.data.WebConfig
import dev.anthonyhfm.kowe.data.WebLoadingState
import dev.anthonyhfm.kowe.data.WebPolicy
import kotlinx.coroutines.flow.StateFlow

interface WebViewState {
    val title: String?
    var location: String?

    var config: WebConfig
        get() = WebConfig()
        set(value) { }

    var policy: WebPolicy?

    val loadingState: StateFlow<WebLoadingState>
    
    var onPageStart: (String?) -> Unit
    var onPageFinish: (String?) -> Unit
    var onMessageReceived: (String) -> Unit

    /**
     * # Console Messages
     *
     * You can set a function as value for [onConsoleMessage] to use the console messages sent by a website in your kowe webview.
     */
    var onConsoleMessage: (ConsoleMessage) -> Unit


    /**
     * # JavaScript Evaluation
     *
     * The [evaluateJavaScript] allows you to run your own JavaScript code in your webview.
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