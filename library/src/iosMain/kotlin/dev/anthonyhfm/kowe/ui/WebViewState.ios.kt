package dev.anthonyhfm.kowe.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.anthonyhfm.kowe.data.JavaScriptResult
import dev.anthonyhfm.kowe.data.WebConfig
import dev.anthonyhfm.kowe.data.WebPolicy
import dev.anthonyhfm.kowe.ui.webkit.AppleWebViewCoordinator
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.javaScriptEnabled

@OptIn(ExperimentalForeignApi::class)
class AppleWebViewState : WebViewState {
    var wkWebView: WKWebView
    val coordinator = AppleWebViewCoordinator()

    override var config: WebConfig = WebConfig()
        set(value) {
            wkWebView.configuration.preferences.javaScriptEnabled = value.enableJavaScript
            wkWebView.customUserAgent = config.userAgent

            field = value
        }

    override var location: String? = null
    override val title: String?
        get() { return wkWebView.title }

    override var policy: WebPolicy? = null
        set(value) {
            coordinator.policy = value

            field = value
        }

    init {
        val configuration = WKWebViewConfiguration()

        wkWebView = WKWebView(
            frame = CGRectZero.readValue(),
            configuration = configuration
        )

        wkWebView.navigationDelegate = coordinator
        wkWebView.UIDelegate = coordinator
    }

    override fun evaluateJavaScript(js: String): JavaScriptResult {
        var callbackResult: String? = null

        wkWebView.evaluateJavaScript(
            javaScriptString = js,
            completionHandler = { result, error ->
                callbackResult = result as? String?
            }
        )

        return JavaScriptResult(result = callbackResult)
    }

    override fun loadUrl(url: String) {
        location = url

        wkWebView.loadRequest(NSURLRequest(NSURL(string = url)))
    }

    override fun loadHtml(html: String) {
        location = null

        wkWebView.loadHTMLString(string = html, baseURL = null)
    }

    override fun reload() {
        wkWebView.reload()
    }
}

@Composable
actual fun rememberWebViewState(
    html: String?,
    config: WebConfig,
    policy: WebPolicy?
): WebViewState {
    val state = remember {
        AppleWebViewState()
    }

    state.config = config
    state.policy = policy

    if (html != null) {
        state.loadHtml(html)
    }

    return state
}

@Composable
actual fun rememberWebViewState(
    url: String,
    config: WebConfig,
    policy: WebPolicy?,
) : WebViewState {
    val state = remember {
        AppleWebViewState()
    }

    state.config = config
    state.policy = policy

    state.loadUrl(url)

    return state
}