package dev.anthonyhfm.kowe.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.anthonyhfm.kowe.data.WebConfig
import dev.anthonyhfm.kowe.data.WebPolicy
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

    override var config: WebConfig = WebConfig()
        set(value) {
            wkWebView.configuration.preferences.javaScriptEnabled = value.enableJavaScript

            field = value
        }

    override var location: String? = null
    override var title: String? = null
    override var policy: WebPolicy? = null

    init {
        val configuration = WKWebViewConfiguration()

        wkWebView = WKWebView(frame = CGRectZero.readValue(), configuration = configuration)
    }

    override fun evaluateJavaScript(js: String) {
        wkWebView.evaluateJavaScript(javaScriptString = js, null)
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
    policy: WebPolicy?
): WebViewState {
    val state = remember {
        AppleWebViewState()
    }

    state.policy = policy

    if (html != null) {
        state.loadHtml(html)
    }

    return state
}

@Composable
actual fun rememberWebViewState(
    url: String,
    policy: WebPolicy?,
) : WebViewState {
    val state = remember {
        AppleWebViewState()
    }

    state.policy = policy
    state.loadUrl(url)

    return state
}