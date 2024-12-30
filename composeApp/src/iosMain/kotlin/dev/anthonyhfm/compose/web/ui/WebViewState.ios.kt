package dev.anthonyhfm.compose.web.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import dev.anthonyhfm.compose.web.data.URLPolicy
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@OptIn(ExperimentalForeignApi::class)
class AppleWebViewState : WebViewState {
    var wkWebView: WKWebView

    override val title: MutableState<String?> = mutableStateOf(null)

    override var policy: URLPolicy? = null

    init {
        val configuration = WKWebViewConfiguration()

        wkWebView = WKWebView(frame = CGRectZero.readValue(), configuration = configuration)
    }

    override fun evaluateJavaScript(js: String) {
        wkWebView.evaluateJavaScript(javaScriptString = js, null)
    }

    override fun navigateUrl(url: String) {
        location.value = url

        wkWebView.loadRequest(NSURLRequest(NSURL(string = url)))
    }

    override fun navigateHtml(html: String) {
        location.value = null

        wkWebView.loadHTMLString(string = html, baseURL = null)
    }

    override fun reload() {
        wkWebView.reload()
    }
}

@Composable
actual fun rememberWebViewState(
    html: String?,
    urlPolicy: URLPolicy?,
): WebViewState {
    val state = remember {
        AppleWebViewState()
    }

    if (html != null) {
        state.navigateHtml(html)
    }

    return state
}

@Composable
actual fun rememberWebViewState(
    url: String,
    urlPolicy: URLPolicy?,
): WebViewState {
    val state = remember {
        AppleWebViewState()
    }

    state.navigateUrl(url)

    return state
}