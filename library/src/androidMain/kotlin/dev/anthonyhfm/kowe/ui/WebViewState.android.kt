package dev.anthonyhfm.kowe.ui

import android.content.Context
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.anthonyhfm.kowe.bridge.KoweMessageInterface
import dev.anthonyhfm.kowe.data.ConsoleMessage
import dev.anthonyhfm.kowe.data.JavaScriptResult
import dev.anthonyhfm.kowe.data.WebConfig
import dev.anthonyhfm.kowe.data.WebLoadingState
import dev.anthonyhfm.kowe.data.WebPolicy
import dev.anthonyhfm.library.generated.resources.Res
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi

class AndroidWebViewState(
    context: Context
) : WebViewState {
    var webkit: WebView = WebView(context)

    override var location: String?
        get() {
            return webkit.url
        }
        set(value) {
            webkit.loadUrl(value ?: "about:blank")
        }

    override val title: String?
        get() { return webkit.title }

    internal val _loadingState: MutableStateFlow<WebLoadingState> = MutableStateFlow(WebLoadingState.Unknown)
    override val loadingState: StateFlow<WebLoadingState> = _loadingState.asStateFlow()

    override var policy: WebPolicy? = null

    override var config: WebConfig = WebConfig()
        set(value) {
            webkit.settings.javaScriptEnabled = value.enableJavaScript
            webkit.settings.userAgentString = value.userAgent

            if (value.enableJsBridge) {
                addBridge()
            } else {
                removeBridge()
            }

            field = value
        }

    override var onPageStart: (String?) -> Unit = { }
    override var onPageFinish: (String?) -> Unit = { }
    override var onMessageReceived: (String) -> Unit = { }
    override var onConsoleMessage: (ConsoleMessage) -> Unit = { }

    override fun evaluateJavaScript(js: String): JavaScriptResult {
        var result: String? = null
        webkit.evaluateJavascript(js) {
            result = it
        }

        return JavaScriptResult(result)
    }

    override fun loadUrl(url: String) {
        location = url
    }

    override fun loadHtml(html: String) {
        location = null

        webkit.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
    }

    override fun reload() {
        webkit.reload()
    }

    private fun addBridge() {
        webkit.addJavascriptInterface(
            KoweMessageInterface(
                onMessage = {
                    onMessageReceived(it)
                }
            ),
            "koweNative"
        )

        webkit.evaluateJavascript(INJECTION_SCRIPT) { }
    }

    private fun removeBridge() {
        webkit.removeJavascriptInterface("koweNative")

        webkit.evaluateJavascript("window.kowe = {};") { }
    }

    @OptIn(ExperimentalResourceApi::class)
    internal val INJECTION_SCRIPT = runBlocking { Res.readBytes("files/bridge_injection.js").decodeToString() }
}

@JvmName("rememberWebViewHtmlState")
@Composable
actual fun rememberWebViewState(
    html: String?,
    config: WebConfig,
    policy: WebPolicy?
): WebViewState {
    val context = LocalContext.current
    val state = remember {
        AndroidWebViewState(context)
    }

    state.config = config
    state.policy = policy

    if (html != null) {
        state.loadHtml(html)
    }

    return state
}

@JvmName("rememberWebViewUrlState")
@Composable
actual fun rememberWebViewState(
    url: String,
    config: WebConfig,
    policy: WebPolicy?,
) : WebViewState {
    val context = LocalContext.current
    val state = remember {
        AndroidWebViewState(context)
    }

    state.config = config
    state.policy = policy

    state.loadUrl(url)

    return state
}